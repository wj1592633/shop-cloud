package com.shop.order.util;

import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.exception.CustomException;
import com.shop.order.vo.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

/**
 * 获取当前用户信息
 */
public class SecurityKit {
    /**
     * 认证信息，有账号，权限，是否认证等信息
     * @return
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 认证详情，有ip，token等信息
     * @return
     */
    public static OAuth2AuthenticationDetails getAuthenticationDetails(){
        return (OAuth2AuthenticationDetails)getAuthentication().getDetails();
    }

    /**
     * 获取token的完整信息。spring oauth2 默认不会把我们CustomUserAuthenticationConverter中附加的
     * 全部信息存到Authentication
     * @return
     */
    public static Map getFullAuthentication(){
        try {
            String token = getAuthenticationDetails().getTokenValue();
            Jwt jwt = JwtHelper.decode(token);
            String claimsStr = jwt.getClaims();
            Map<String, Object> claims = JsonParserFactory.create().parseMap(claimsStr);
            return claims;
        } catch (Exception var6) {
            throw new InvalidTokenException("Cannot convert access token to JSON", var6);
        }
    }
    /**
     * 获取当前用户账号
     * @return
     */
    public static String getCurAccount(){
        return getAuthentication().getName();
    }

    /**
     * 获取当前用户id
     * @return
     */
    public static String getUserId(){
        String userId =  (String)(getFullAuthentication().get(SecurityProperties.ID));
        if (StringUtils.isBlank(userId)){
            throw new CustomException(ExceptionEnum.NEED_LOGIN);
        }
        return userId;
    }

    /**
     * 根据传过来的id判断是否为当前登录id
     * @param id
     * @return
     */
    public static boolean isCurrentUser(String id){
        if (StringUtils.isBlank(id)){
            return false;
        }
        return id.equals(getUserId());
    }
}