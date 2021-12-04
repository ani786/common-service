package com.nswlrs.api.common.commonservice.external.interceptor;

import static com.nswlrs.api.common.commonservice.util.Constants.AUTHORIZATION_HEADER;
import static com.nswlrs.api.common.commonservice.util.Constants.X_CORRELATION_ID;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PortalRequestInterceptor implements RequestInterceptor {

    private final RequestClientHeaderInterceptor requestClientHeaderInterceptor;

    @Override
    public void apply(RequestTemplate template) {
        log.info("adding request headers");
        template.header(AUTHORIZATION_HEADER, requestClientHeaderInterceptor.apply());
        template.header(X_CORRELATION_ID, MDC.getCopyOfContextMap().get(X_CORRELATION_ID));
    }
}
