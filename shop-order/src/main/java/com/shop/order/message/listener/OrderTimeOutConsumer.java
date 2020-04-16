package com.shop.order.message.listener;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.constant.Constant;
import com.shop.common.constant.RedisConstant;
import com.shop.common.enumation.OrderStateEnum;
import com.shop.order.service.IOrderService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 用于监事务听消息更新订单
 */
@Component
@RocketMQMessageListener(consumerGroup = Constant.TXMSG_PRODUCER_GROUP_ORDER_TIME_OUT,topic = Constant.TXMSG_TOPIC_ORDER_TIME_OUT)
public class OrderTimeOutConsumer implements RocketMQListener<String> {
    @Autowired
    IOrderService orderService;
    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;
    @Override
    @Transactional
    public void onMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String orderId = (String) jsonObject.get(Constant.TIME_OUT_ITEM_DB_SKU_KEY);
        String skuKey = (String) jsonObject.get(Constant.TIME_OUT_ITEM_REDIS_KEY);
        //更改订单状态
        int i = orderService.handleOrderTimeOut(orderId);
        //redis对应的库存+1
        if (i == 1){
            redisTemplate.boundHashOps(skuKey).increment(RedisConstant.SKU_RESERVE, 1);
        }

    }
}
