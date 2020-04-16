package com.shop.api.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启feign日志，再在properties文件添加logging.level.com.shop.api.feign.goods.service.GoodService= debug
 */
//@Configuration
public class FeignConfig {
    //@Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
