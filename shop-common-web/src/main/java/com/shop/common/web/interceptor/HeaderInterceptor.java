package com.shop.common.web.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
/**
 * feign间调用并向下传递头信息
 * 该拦截器可能会频繁被用，所有最好写到一个公共模块。
 * 公共模块中得话暂时不用被加载到spring容器，哪个模块要用该拦截器，就@Bean在new出来
 * 还要在配置文件中配置hystrix的strategy
 */
public class HeaderInterceptor  implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if(headerNames != null){
                //遍历，把所有得header向下传
                while (headerNames.hasMoreElements()){
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    //向下传递头信息
                    requestTemplate.header(headerName,headerValue);
                }
            }
        }

    }
}