package com.shop.user.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@ConfigurationProperties(prefix = "shop.security")
@EnableConfigurationProperties //记得加上这个注解
@Component
@Data
public class SecurityProperties implements Serializable{
    private String clientId;
    private String clientSecret;
    private String getTokenUrl;
    private String signingKey;
    private int expire = 14400; //token有效时间，默认4小时
    private int refreshExpire = 1800; //refresToken有效时间
    private String[] redirectUris;
    private String[] grantTypes;
    private String[] scopes;
    private String[] anonUri;
    private String keyStorePath;
    private String keyStorePass;
    private String keyPairAlias;
    private String keyPairPass;
    private String refreshTokenKey = "refresh_jwt";

    public final static String USER_NAME = "user_name"; //当前用户账号
    public final static String ID = "id"; //当前用户id
    public final static String NOTE = "note"; //当前用户标记
    public final static String AUTHORITIES = "authorities";//当前用户的权限集合,List<String>
    public final static String AUD = "aud"; //client的RESOURCE_ID
    public final static String EXP = "exp"; //token有效时间
    public final static String JTI = "jti"; //token的jti
    public final static String CLIENT_ID = "client_id"; //client_id
    public final static String IS_ADMIN = "ifAdmin";
}
