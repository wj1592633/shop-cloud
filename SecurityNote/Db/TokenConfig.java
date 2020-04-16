package com.shop.user.config.security;

import com.shop.user.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
public class TokenConfig {

    @Autowired
    SecurityProperties properties;
    @Autowired
    CustomUserAuthenticationConverter customUserAuthenticationConverter;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        /**
         * JwtAccessTokenConverter中有个AccessTokenConverter类，可以进行相关配置，设置附加信息到jwt中.最好配置这个，不然麻烦
         *
         * 在customUserAuthenticationConverter中给token的Claims添加user_name和authorities，oauth2的过滤器会把用户权限解析放到SecurityContextHolder中，
         * 不添加authorities的话要手动给SecurityContextHolder设置用户的权限等，会麻烦
         * DefaultAccessTokenConverter的extractAuthentication()方法解析authorities出权限，放入SecurityContextHolder
         */
        ((DefaultAccessTokenConverter)jwtAccessTokenConverter.getAccessTokenConverter()).setUserTokenConverter(customUserAuthenticationConverter);

        //设置签名
        jwtAccessTokenConverter.setSigningKey(properties.getSigningKey());
        //myJwtClaimsSetVerifier可以进行对token解析后的内容第二次校验
        //jwtAccessTokenConverter.setJwtClaimsSetVerifier(myJwtClaimsSetVerifier());
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


   /* @Bean
    public JwtClaimsSetVerifier myJwtClaimsSetVerifier(){
        return new MyJwtClaimsSetVerifier();
    }*/


}