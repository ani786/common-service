package com.nswlrs.api.common.commonservice.filter;

import static com.nswlrs.api.common.commonservice.util.Constants.AUTHORIZATION_HEADER;
import static com.nswlrs.api.common.commonservice.util.Constants.X_ACTIONED_BY;
import static org.apache.commons.lang3.BooleanUtils.TRUE;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nswlrs.api.common.commonservice.external.rest.oauth.JwtTokenValues;
import com.nswlrs.api.common.commonservice.external.utils.JsonUtils;
import com.nswlrs.api.common.commonservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * <p>
 * The Auth JWT validtor.
 * </p>
 * 1. check it is health check or swagger or open api link or not
 * 2. check token is null or not
 * 3. check the format of the token
 * 4. check the permissions of the request
 * any one of them is failure will not be allowed to go further
 */
@Slf4j
@Component
@Order(2)
@Disabled
public class AuthTokenValidator implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${security.permissions}")
    private String allowedPermissions;

    @Value("${security.enabled}")
    private String enabled;

    @Override
    public void init(FilterConfig arg0) {
    }

    @Override
    public void destroy() {
    }

    /**
     * <p>
     * The main class to capture the input and do validation.
     * </p>
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param chain    FilterChain for the whole workflow
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestUri = httpServletRequest.getRequestURL().toString();
        if (enabled == null || !TRUE.equalsIgnoreCase(enabled)) {
            chain.doFilter(request, response);
        } else {
            if (!requestUri.contains("/actuator/health") && !requestUri.contains("/swagger") &&
                        !requestUri.contains("/api-docs") &&
                        !requestUri.contains("/actuator/refresh")) {
                String authorizationToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
                String actionedBy = httpServletRequest.getHeader(X_ACTIONED_BY);
                log.info("the user: {} with token: {} ", actionedBy, authorizationToken);
                if (authorizationToken == null) {
                    log.error("Token should be not null with user: {} ", actionedBy);
                    setHttpServletResponse(httpServletResponse, Constants.AUTH_TOKEN_ISSUE_MESSAGE);
                } else {
                    String[] tokens = StringUtils.split(authorizationToken, " ");
                    if (checkPermission(tokens[1], httpServletRequest, httpServletResponse)) {
                        chain.doFilter(request, response);
                    }
                }
            } else if (requestUri.contains("/swagger") || requestUri.contains("/api-docs")) {
                chain.doFilter(request, response);
            } else {
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                httpServletResponse.setStatus(HttpStatus.OK.value());
                httpServletResponse.getWriter().write("{\"status\": \"UP\"}");
            }
        }
    }

    /**
     * <p>
     * To check the permissions of the token.
     * </p>
     *
     * @param authorizationToken  the input jwt token from header
     * @param httpServletRequest  the input request
     * @param httpServletResponse the output response
     * @return true if permit otherwise false
     * @throws IOException IOException
     */
    private boolean checkPermission(final String authorizationToken, HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse)
                throws IOException {
        String[] tokens = authorizationToken.split("\\.");
        if (tokens.length < 3) {
            log.error("The format of the authorization token is invalid with only {} parts ", tokens.length);
            setHttpServletResponse(httpServletResponse, Constants.AUTH_TOKEN_ISSUE_MESSAGE);
            return false;
        } else {
            byte[] decodebytes = Base64.getDecoder().decode(tokens[1]);
            String decodeValues = new String(decodebytes, StandardCharsets.UTF_8);
            JwtTokenValues tokenValues = objectMapper.readValue(decodeValues, JwtTokenValues.class);
            //log.info("the refreshhandler : {} ", refreshHandler.getAllowedPermissions());
            Map<String, Object> permissionMap = JsonUtils.convertStringToMap(allowedPermissions);
            if (!isAllowed(permissionMap, httpServletRequest, tokenValues.getPermissions())) {
                log.error("the user is not allowed to access the api {} ", tokenValues.getPermissions());
                setHttpServletResponse(httpServletResponse, Constants.AUTH_TOKEN_ISSUE_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Logic to check whether the requestURI or context path and servlet path need to be used for validation.
     * </p>
     *
     * @param permissionMap      The permissions are allowed to access the microservice operation
     * @param httpServletRequest input request
     * @param tokenPermissions   the permissions belong to the user
     * @return true is allowed otherwise false
     */
    private boolean isAllowed(final Map<String, Object> permissionMap, final HttpServletRequest httpServletRequest,
                              final List<String> tokenPermissions) {
        String url = httpServletRequest.getRequestURI();
        List<Map<String, Object>> permissionList =
                    (List<Map<String, Object>>) permissionMap.get(Constants.PERMISSION_MAPPING);
        List<String> endpoints =
                    permissionList.stream().map(permission -> (String) permission.get(Constants.END_POINT))
                                .collect(Collectors.toList());
        if (endpoints.contains(url)) {
            return isAllowedFromEndPointWithRequestUri(permissionList, httpServletRequest, tokenPermissions);
        }
        return isAllowedFromContextAndServletPaths(permissionList, httpServletRequest, tokenPermissions);
    }

    /**
     * <p>
     * Validate the jwt token by using Context path and servlet path.
     * </p>
     *
     * @param permissionList     The permissions are allowed to access the microservice operation
     * @param httpServletRequest input request
     * @param tokenPermissions   the permissions belong to the user
     * @return true if allowed otherwise false
     */
    public boolean isAllowedFromContextAndServletPaths(final List<Map<String, Object>> permissionList,
                                                       final HttpServletRequest httpServletRequest,
                                                       final List<String> tokenPermissions) {
        log.info("inside isAllowedFromContextAndServletPaths");
        String method = httpServletRequest.getMethod();
        String contextPath = httpServletRequest.getContextPath();
        String servletPath = httpServletRequest.getServletPath();

        for (Map<String, Object> map : permissionList) {
            String endpoint = (String) map.get(Constants.END_POINT);
            if (contextPath.equals(endpoint)) {
                List<Map<String, Object>> metaDatas =
                            (List<Map<String, Object>>) map.get(Constants.META_DATA);
                for (Map<String, Object> metaData : metaDatas) {
                    String action = (String) metaData.get(Constants.ACTION);
                    String servletName = (String) metaData.get(Constants.SERVLET_NAME);
                    String attributes = (String) metaData.get(Constants.ATTRIBUTES);
                    String[] permissions = StringUtils.split((String) metaData.get(Constants.PERMISSIONS), ",");
                    log.info("fetched permission from store {} for uri {}, method {}", Arrays.toString(permissions),
                                endpoint, method);
                    if (action.equals(method)) {
                        String[] elements = StringUtils.split(servletPath, "/");
                        if (elements.length > 1) {
                            String actualServletName = "/" + elements[0] + "/" + elements[1];
                            if (servletName.equals(actualServletName) &&
                                        elements.length - 2 == Integer.parseInt(attributes)) {
                                return isAllowedToContinue(tokenPermissions, permissions);
                            }
                        } else if (elements.length > 0) {
                            String actualServletName = "/" + elements[0];
                            if ((servletName.equals(actualServletName) ||
                                        servletName.equals(actualServletName + "/")) &&
                                        elements.length - 1 == Integer.parseInt(attributes)) {
                                return isAllowedToContinue(tokenPermissions, permissions);
                            }

                        } else {
                            return isAllowedToContinue(tokenPermissions, permissions);
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * <p>
     * Validate the jwt token by using requestURI path.
     * </p>
     *
     * @param permissionList     The permissions are allowed to access the microservice operation
     * @param httpServletRequest input request
     * @param tokenPermissions   the permissions belong to the user
     * @return true if allowed otherwise false
     */
    public boolean isAllowedFromEndPointWithRequestUri(final List<Map<String, Object>> permissionList,
                                                       final HttpServletRequest httpServletRequest,
                                                       final List<String> tokenPermissions) {
        log.info("inside isAllowedFromEndPointWithRequestUri");
        String url = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();

        for (Map<String, Object> map : permissionList) {
            String endpoint = (String) map.get(Constants.END_POINT);
            if (url.equals(endpoint)) {
                List<Map<String, Object>> metaDatas =
                            (List<Map<String, Object>>) map.get(Constants.META_DATA);
                for (Map<String, Object> metaData : metaDatas) {
                    String action = (String) metaData.get(Constants.ACTION);
                    String[] permissions = StringUtils.split((String) metaData.get(Constants.PERMISSIONS), ",");
                    log.info("fetched permission from store {} for uri {}, method {}", Arrays.toString(permissions),
                                url, method);
                    if (action.equals(method)) {
                        return isAllowedToContinue(tokenPermissions, permissions);
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * <p>
     * Check whether the input request is allowed to go ahead.
     * </p>
     *
     * @param tokenPermissions the permissions belong to the user
     * @param permissions      he permissions are allowed to access the microservice operation
     * @return true if allowed otherwise false
     */
    public boolean isAllowedToContinue(final List<String> tokenPermissions, String[] permissions) {
        List<String> actualPermissions = Arrays.asList(permissions);
        return tokenPermissions.removeAll(actualPermissions);
    }

    /**
     * <p>
     * Set the HttpServletResponse and attach error message to it.
     * </p>
     *
     * @param httpServletResponse output response back to the user
     * @param errorMessage        the error message
     * @throws IOException IOException
     */
    private void setHttpServletResponse(HttpServletResponse httpServletResponse, String errorMessage)
                throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter()
                    .write("not allowed");
    }

    /**
     * <p>
     * Construct the concise error message.
     * </p>
     *
     * @param errorMessage the erorr message
     * @return ErrorResponse
     */
    //private ErrorResponse constructError(String errorMessage) {
    // return new ErrorResponse(null, errorMessage);
    // }
}
