package com.shop.user.service;

import com.shop.common.dto.ResponseResult;
import com.shop.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.user.vo.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;

/**
 * <p>
 * 用户表，简单处理，不分卖主和买主 服务类
 * </p>
 *
 * @author wj
 */
public interface IUserService extends IService<User> {
    public void sendMsg();
    public UserInfo login(User user);

    ResponseResult prePayForOrder(String orderId, String userId);
    /**
     * 模拟支付
     * @param orderId
     * @return
     */
    ResponseResult payForOrder(String orderId);

    /**
     * 获取用户余额
     * @return
     */
    public BigDecimal getAmount();

    Object create();

    Object getOrder();
}
