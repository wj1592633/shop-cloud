package com.shop.user.config.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.exception.CustomException;
import com.shop.common.thread.ThreadData;
import com.shop.entity.User;
import com.shop.user.service.IUserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //想使用静态数据，可以把下面一行的注释放开，其余的注释,还有CustomUserAuthenticationConverter也要注释
        //return new JwtUser(username,passwordEncoder.encode("111111"),AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN,ROLE_USER,menu"));
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        //根据账号查询用户
        User user = userService.getOne(wrapper);
        if(user == null){
            throw new CustomException(ExceptionEnum.ERROR_ACCOUNT);
        }
        //权限，暂时模拟静态数据
        String permissions = "user/list,goods/list,order/add";
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getUsername(),user.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
        //state必须设置上去，security后面会校验userDetails的state
        userDetails.setState(user.getState());
        userDetails.setUserId(user.getUserId());
        return userDetails;
    }

}