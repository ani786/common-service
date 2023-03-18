package com.mycommonservref.api.common.commonservice.external.filter;

import static com.mycommonservref.api.common.commonservice.util.Constants.APPLY_TO_EXTERNAL_URI;
import static com.mycommonservref.api.common.commonservice.util.Constants.AUTHORIZATION_HEADER;
import static com.mycommonservref.api.common.commonservice.util.Constants.VERSION2;
import static com.mycommonservref.api.common.commonservice.util.Constants.X_ACTIONED_BY;
import static com.mycommonservref.api.common.commonservice.util.Constants.X_CORRELATION_ID;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycommonservref.api.common.commonservice.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;

@Component
@Order(1)
@Slf4j
public class LoggingFilter implements Filter {

    private final Consumer<HttpServletRequest> captureCorrelationId = request -> {
        String correlationId = request.getHeader(X_CORRELATION_ID);

        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }
        MDC.put(X_CORRELATION_ID, correlationId);
    };
    private final Consumer<HttpServletRequest> captureActionedBy = request -> {
        try {
            String authorizationToken = request.getHeader(AUTHORIZATION_HEADER);
            if (authorizationToken != null) {
                String[] tokens = authorizationToken.split("\\.");
                if (!(tokens.length < 3)) {
                    byte[] decodebytes = Base64.getDecoder().decode(tokens[1]);
                    String decodeValues = new String(decodebytes, StandardCharsets.UTF_8);
                    Map<String, String> authMap = new ObjectMapper().readValue(decodeValues, HashMap.class);
                    MDC.put(X_ACTIONED_BY, authMap.get("sub"));

                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error while fetching idp info {}", e.getMessage());
        }
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
            String requestUri = httpServletRequest.getRequestURI();

            if (APPLY_TO_EXTERNAL_URI.stream().anyMatch(requestUri::startsWith)) {
                if (requestUri.contains(VERSION2) &&
                            StringUtils.isEmpty(httpServletRequest.getHeader(X_CORRELATION_ID))) {
                    throw new ServletRequestBindingException("Missing required header X-Correlation-Id");
                }
                captureCorrelationId.andThen(captureActionedBy).accept(httpServletRequest);
            }
        } catch (ServletRequestBindingException srEx) {
            log.error(srEx.getMessage());
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ErrorResponse errorResponse =
                        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), srEx.getMessage(),
                                    srEx.getMessage(),
                                    "");
            ObjectMapper mapper = new ObjectMapper();
            PrintWriter out = res.getWriter();
            out.print(mapper.writeValueAsString(errorResponse));
            out.flush();
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
