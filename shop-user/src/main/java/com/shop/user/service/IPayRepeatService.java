package com.shop.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.entity.PayRepeat;

/**
 * <p>
 * ֧ 服务类
 * </p>
 *防止同一个订单重复支付
 * @author wj
 */
public interface IPayRepeatService extends IService<PayRepeat> {

}
