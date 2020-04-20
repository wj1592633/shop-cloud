package com.shop.user.controller;


import com.shop.common.dto.ResponseResult;
import com.shop.entity.User;
import com.shop.user.service.IUserService;
import com.shop.user.vo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Enumeration;


/**
 * <p>
 * 用户表，简单处理，不分卖主和买主 前端控制器
 * </p>
 *
 * @author wj
 */
@RestController
public class UserController {
    @Autowired
    IUserService userService;

    /**
     * 登录入口
     * @param user
     * @return
     */
   @PostMapping("/oauth/login")
   public ResponseResult login(User user, HttpServletRequest request){
       if (null == user || StringUtils.isBlank(user.getUsername())
               || StringUtils.isBlank(user.getPassword())
               ){
           return ResponseResult.fail("账号或密码不能为空");
       }
       UserInfo userInfo = userService.login(user);
       if (null != userInfo) {
           return ResponseResult.ok(userInfo);
       } else {
           return ResponseResult.fail("账号或密码错误");
       }
   }
    //不应从前端传入userId，实际也没作用
    @GetMapping("/user/pay")
    public ResponseResult payForOrder(String orderId, String userId){
       return userService.prePayForOrder(orderId, userId);
    }
    @GetMapping("/user/amount")
    public ResponseResult getAmount(){
        BigDecimal amount = userService.getAmount();
        return ResponseResult.ok(amount);
    }
    @GetMapping("/user/order")
    public Object getOrder(){
        Object amount = userService.getOrder();
        return ResponseResult.ok(amount);
    }

    @PreAuthorize("hasAuthority('user/list')")
    @GetMapping("/test1")
    public Object payForOrder1(String orderId){
       return orderId;
    }
    @PreAuthorize("hasAuthority('/role/add')")
    @GetMapping("/test2")
    public Object payForOrder2(String orderId){
        return orderId + ":::aaaaaa";
    }


     @GetMapping("/create/token")
     public Object createToken(){
       return userService.create();
     }
}
