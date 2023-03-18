package com.mycommonservref.api.common.commonservice.filter;

import static com.mycommonservref.api.common.commonservice.util.Constants.MISSING_REQUEST_HEADER;
import static com.mycommonservref.api.common.commonservice.util.Constants.REGEX_FOR_PASS_THROUGH_URLS;
import static com.mycommonservref.api.common.commonservice.util.Constants.X_ACTIONED_BY;
import static com.mycommonservref.api.common.commonservice.util.Constants.X_CORRELATION_ID;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycommonservref.api.common.commonservice.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;

/**
 * The type Log filter.
 */
@Component
@Order(1)
@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        MDC.clear();

        if (isMandatoryHeadersPresent((HttpServletRequest) request, (HttpServletResponse) response)) {
            addAllCustomHeadersToMDC((HttpServletRequest) request);
            addAPIGeneratedUUIDWhenCorrelationIDNotPresent();
            chain.doFilter(request, response);
        }

    }

    private boolean isMandatoryHeadersPresent(HttpServletRequest request, HttpServletResponse response)
                throws IOException {
        if (request.getRequestURI().matches(REGEX_FOR_PASS_THROUGH_URLS)) {
            return true;
        } else if (StringUtils.isEmpty(request.getHeader(X_ACTIONED_BY))) {
            try {
                request.getHeaderNames().asIterator().forEachRemaining(header -> {
                    if (X_ACTIONED_BY.equalsIgnoreCase(String.valueOf(header))) {
                        throw new ValidationException("missing value for required header " + X_ACTIONED_BY);
                    } else {
                        throw new ValidationException("missing required header " + X_ACTIONED_BY);
                    }
                });

            } catch (ValidationException validationException) {
                log.error(validationException.getMessage());
                validateMandatoryHeadersErrorResponse(response, validationException);
                return false;
            }

        } else if (StringUtils.isEmpty(request.getHeader(X_CORRELATION_ID))) {
            try {
                 request.getHeaderNames().asIterator().forEachRemaining(header->{
                    if (X_CORRELATION_ID.equalsIgnoreCase(String.valueOf(header))){
                        throw new ValidationException("missing value for required header "+X_CORRELATION_ID);
                    }else{
                        throw new ValidationException("missing required header "+X_CORRELATION_ID);
                    }
                });
            } catch (ValidationException validationException) {
                log.error(validationException.getMessage());
                validateMandatoryHeadersErrorResponse(response, validationException);
                return false;
            }

        }
        return true;
    }

    private void validateMandatoryHeadersErrorResponse(HttpServletResponse response, Exception exception)
                throws IOException {
        HttpServletResponse res = response;
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), MISSING_REQUEST_HEADER,
                    exception.getMessage(), MissingRequestHeaderException.class.getSimpleName());
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = res.getWriter();
        out.print(mapper.writeValueAsString(errorResponse));
        out.flush();
    }

    private void addAllCustomHeadersToMDC(HttpServletRequest request) {
        request.getHeaderNames().asIterator().forEachRemaining(header -> {
            if (X_ACTIONED_BY.equalsIgnoreCase(String.valueOf(header))) {
                MDC.put(X_ACTIONED_BY, request.getHeader(String.valueOf(header)));
            } else if (X_CORRELATION_ID.equalsIgnoreCase(String.valueOf(header))) {
                MDC.put(X_CORRELATION_ID, request.getHeader(String.valueOf(header)));
            }
        });

    }

    private void addAPIGeneratedUUIDWhenCorrelationIDNotPresent() {
        if (MDC.getCopyOfContextMap() == null) {
            generateUUID();
        } else if (MDC.getCopyOfContextMap() != null &&
                    StringUtils.isEmpty(MDC.getCopyOfContextMap().get((X_CORRELATION_ID)))) {
            generateUUID();
        }
    }

    private void generateUUID() {
        UUID requestId = UUID.randomUUID();
        MDC.put(X_CORRELATION_ID, String.valueOf(requestId));
    }

    @Override
    public void destroy() {

    }
}
