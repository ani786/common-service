package com.mycommonservref.api.common.commonservice.external.config;


import com.mycommonservref.api.common.commonservice.external.interceptor.PortalRequestInterceptor;
import com.mycommonservref.api.common.commonservice.external.interceptor.RequestClientHeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class FeignClientConfiguration {
    @Autowired
    private RequestClientHeaderInterceptor requestClientHeaderInterceptor;

    @Bean
    public FeignErrorDecoder clientErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public PortalRequestInterceptor lodgingPartyRequestInterceptor() {
        return new PortalRequestInterceptor(requestClientHeaderInterceptor);
    }
}
