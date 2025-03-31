package com.kharevich.rideservice.util.config;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor feignTracingInterceptor() {
        return new TracingFeignInterceptor();
    }
}
