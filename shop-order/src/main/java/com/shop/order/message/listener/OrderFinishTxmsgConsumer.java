package com.shop.order.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.constant.Constant;
import com.shop.common.enumation.OrderStateEnum;
import com.shop.order.service.IOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于监事务听消息更新订单
 */
@Component
@RocketMQMessageListener(consumerGroup = Constant.TXMSG_CONSUMER_GROUP_ORDER,topic = Constant.TXMSG_TOPIC_ORDER_STATE)
public class OrderFinishTxmsgConsumer implements RocketMQListener<String> {
    @Autowired
    IOrderService orderService;

    @Override
    @Transactional
    public void onMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String orderId = (String)jsonObject.get("orderId");
        //更改订单状态
        int i = orderService.payOrderById(orderId, OrderStateEnum.NEED_PAY.getStateNum(), OrderStateEnum.HAD_PAY.getStateNum());
    }
}
