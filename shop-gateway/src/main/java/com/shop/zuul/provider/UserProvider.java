package com.shop.zuul.provider;

import org.springframework.stereotype.Component;

/**
 * 当通过zuul访问shop-user模块出错时，会调用此类的fallbackResponse方法
 */
//@Component
public class UserProvider extends BaseProvider {
    @Override
    public String getRoute() {
        return "shop-user";
    }
}
