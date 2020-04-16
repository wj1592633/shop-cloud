package com.shop.user.message;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.constant.Constant;
import com.shop.entity.PayRepeat;
import com.shop.user.service.IPayRepeatService;
import com.shop.user.service.IUserService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RocketMQTransactionListener(txProducerGroup= Constant.TXMSG_PRODUCER_GROUP_USER)
public class ProducerTxmsgListener implements RocketMQLocalTransactionListener {
    @Autowired
    IUserService userService;
    @Autowired
    IPayRepeatService payRepeatService;
    /**
     * 事务消息发送到mq但标记为不可用成功后，调用此方法，进行实际数据库操作
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //解析消息
        Object payload = message.getPayload();
        String s = new String((byte[]) payload);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String orderId = (String)jsonObject.get("orderId");
        //========进行实际数据库操作========"
        userService.payForOrder(orderId);
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //========进行重复检验回查========"
        //解析消息
        Object payload = message.getPayload();
        String s = new String((byte[]) payload);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String orderId = (String)jsonObject.get("orderId");
        PayRepeat byId = payRepeatService.getById(orderId);
        //PayRepeat有数据则，已经完成
        if (null != byId) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
          return RocketMQLocalTransactionState.UNKNOWN;
        }

    }
}
