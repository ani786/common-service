package com.nswlrs.api.common.commonservice.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The type Document filter.
 */
@Component
@Order(1)
public class CORSfilter implements Filter {

    @Value("${origin.allowed}")
    private String originAllowed;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (originAllowed.contains("dev")) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        } else {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", originAllowed);
        }
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                    "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,X-Actioned-By," +
                                "X-Correlation-Id");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
