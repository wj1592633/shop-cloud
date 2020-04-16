package com.shop.user.config.security;

import com.shop.user.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;


    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap response = new LinkedHashMap();
        String name = authentication.getName();
        //这个必须写，而且必须为user_name.不写的话，会导致，SecurityContextHolder.getContext.getAuthentication.userAuthentication为null，就是获取不到登陆用户的信息
        //具体见DefaultUserAuthenticationConverter的extractAuthentication()方法
        response.put(SecurityProperties.USER_NAME, name);

        Object principal = authentication.getPrincipal();
        UserDetailsImpl userDetails = null;
        if(principal instanceof  UserDetailsImpl){
            userDetails = (UserDetailsImpl) principal;
        }else{
            //refresh_token默认不去调用userdetailService获取用户信息，这里我们手动去调用，得到 JwtUser
            UserDetails userDetails1 = userDetailsService.loadUserByUsername(name);
            userDetails = (UserDetailsImpl) userDetails1;
        }

        response.put(SecurityProperties.ID, userDetails.getUserId());


        //做权限控制的话，这个必须写，而且必须为authorities
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(SecurityProperties.AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }


}