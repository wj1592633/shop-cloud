package com.shop.user.config.feign;

import com.shop.common.web.interceptor.HeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignHeaderConfig {
    @Bean
    public HeaderInterceptor headerInterceptor(){
        return new HeaderInterceptor();
    }
}
