package com.nswlrs.api.common.commonservice.external.interceptor;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class RequestClientHeaderInterceptor {

    private static final String AUTHORIZATION = "Authorization";

    public String apply() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(AUTHORIZATION);
        log.info("the token is: {} ", token);
        return token;
    }
}
