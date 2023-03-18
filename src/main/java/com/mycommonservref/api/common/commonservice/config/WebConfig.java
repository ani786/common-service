package com.mycommonservref.api.common.commonservice.config;

import javax.servlet.Filter;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type X ray configuration.
 */
@Configuration
public class WebConfig {

    @Value("${xray.tracing.name}")
    private String tracingName;

    /**
     * Tracing filter filter.
     *
     * @return the filter
     */
    @Bean
    public Filter tracingFilter() {

        return new AWSXRayServletFilter(tracingName);

    }

}
