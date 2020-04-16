package com.shop.order.service;

import com.shop.common.dto.ResponseResult;
import com.shop.entity.GoodsSku;
import com.shop.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.entity.util.PageUtil;
import com.shop.order.vo.OderData;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wj
 */
public interface IOrderService extends IService<Order> {
    /**
     * 下单
     * @param data
     * @return true ->下单成功, false ->失败
     */
    ResponseResult addOrder(OderData data);

    /**
     * 根据用户id查询订单
     * @param pageUtil
     * @return
     */
    PageUtil<Order> getOrderByUserId(PageUtil<Order> pageUtil);

    /**
     * 根据订单id更改订单状态
     * @param orderId
     * @param expectState 期望的状态
     * @param toState 最终修改成的状态
     * @return
     */
    int payOrderById(Serializable orderId, Integer expectState, Integer toState);

    /**
     * 订单逾时未支付，根据订单id修改订单状态
     * @param orderId
     * @return
     */
    int handleOrderTimeOut(String orderId);
}
