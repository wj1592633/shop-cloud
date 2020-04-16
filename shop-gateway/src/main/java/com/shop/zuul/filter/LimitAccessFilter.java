package com.shop.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Component
public class LimitAccessFilter extends ZuulFilter {
    /**
     * 前，后，异常
     * @return
     */
    @Override
    public String filterType() {
        //pre,routing,post,error
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否需要过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //过滤逻辑
        //获取request对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String key = request.getRemoteAddr()+ ":" + request.getRequestURI();
        //根据key去redis查，如此次数1分钟内有60次则禁止访问
        if(false){
            //拒绝访问
            currentContext.setSendZuulResponse(false);
            //无权限
            currentContext.setResponseStatusCode(400);
            HttpServletResponse response = currentContext.getResponse();
            response.setContentType("application/json;charset=utf-8");
            try {
                Map result = new HashMap<>();
                result.put("state",400);
                result.put("fail","您访问过于频繁，请休息一下再重试！");
                String value = new ObjectMapper().writeValueAsString(result);
                response.getOutputStream().write(value.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
