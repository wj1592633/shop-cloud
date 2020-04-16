package com.shop.user.vo;

import com.shop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo extends User {
    //jwt令牌(用户携带这个)
    private String jwtToken;
    //刷新令牌
    private String refreshToken;
    //最短得令牌
    private String tokenKey;
}
