package com.shop.order.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 资源服务器，EnableResourceServer相当添加了一个filter
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "user_service";

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenStore tokenStore;

   /* @Bean
    public OAuth2AuthenticationEntryPoint refrshAuthenticationEntryPoint(){
        RefrshAuthenticationEntryPoint entryPoint = new RefrshAuthenticationEntryPoint();
        entryPoint.setProperties(SpringContextHolder.getBean(MBSecurityProperties.class));
        return entryPoint;
    }*/

    /**
     * 【】ResourceServerSecurityConfigurer类的tokenServices()方法会主动生成DefaultTokenServices,
     * 然后把tokenStore设置进DefaultTokenServices，
     *【】这里的tokenStore是自己手动生成的，并设置了jwtAccessTokenConverter
     * 【】DefaultTokenServices会用jwthelper解析并用签名校验token，然后又在loadAuthentication()校验token是否过期
     * @param resources
     *
     * OAuth2AuthenticationManager会在authenticate()校验解析后的token是否包含RESOURCE_ID，
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                //.authenticationEntryPoint(refrshAuthenticationEntryPoint())
                .resourceId(RESOURCE_ID)
                .tokenStore(tokenStore) //配置来校验token
                .stateless(true);  //无状态登录，不记录session
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/test/**").permitAll()
                //客户端发令牌时配置了scope，这里可以配置校验，不配置也是可以的
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .anyRequest().authenticated();
    }
}
