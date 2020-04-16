package com.shop.user.config.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.Collection;
@Data
public class UserDetailsImpl extends User {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 盐
     */
    private String salt;

    /**
     * 余额
     */
    private BigDecimal amount;

    /**
     * 状态
     */
    private Integer state;


    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        super.isEnabled();
        return state.equals(1);
    }
}
