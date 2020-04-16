package com.shop.order.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTypeConfig {

    public static final String pattern1 = "yyyy-MM-dd";
    public static final String pattern2 = "yyyy-MM-dd HH:mm:ss";
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(){
        return builder -> {
          builder.simpleDateFormat(pattern2);
          builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(pattern1)));
          builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern2)));
        };
    }
}
