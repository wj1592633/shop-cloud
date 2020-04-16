package com.shop.user.config.interceptor;

import com.shop.user.interceptor.FirstFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;

//@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean firstFilter(){
        FirstFilter firstFilter = new FirstFilter();
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        bean.setFilter(firstFilter);
        ArrayList list = new ArrayList();
        //这里不能写成 /**
        list.add("/*");
        bean.setUrlPatterns(list);
        bean.setEnabled(true);
        bean.setOrder(Integer.MIN_VALUE);
        return bean;
    }
}
