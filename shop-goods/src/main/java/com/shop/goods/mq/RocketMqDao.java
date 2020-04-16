package com.shop.goods.mq;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.constant.Constant;
import com.shop.common.dto.TxMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class RocketMqDao {
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    public void sendTxmsg(TxMessage message){
        //转成字符串
        String messageStr = JSONObject.toJSONString(message);
        //把字符串放Message对象中
        Message<String> message1 = MessageBuilder.withPayload(messageStr).build();
        //发送消息
        rocketMQTemplate.sendMessageInTransaction(Constant.TXMSG_PRODUCER_GROUP_GOODS, Constant.TXMSG_TOPIC_ORDER_GENERATE,
                message1,null);
    }
}
