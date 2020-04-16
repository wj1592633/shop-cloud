/*
package com.shop.goods.mq.listener;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.constant.Constant;
import com.shop.common.dto.TxMessage;
import com.shop.goods.mongo.dto.BuyRecord;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RocketMQTransactionListener(txProducerGroup= Constant.TXMSG_PRODUCER_GROUP_GOODS)
public class OrderProducerTxMsgListener implements RocketMQLocalTransactionListener {
    @Autowired
    BuyRecordMongoDao buyRecordMongoDao;

    */
/**
     * 直接进行确认，对reduceReserve方法中发到订单模块的mq事务消息直接进行确认
     * ---------------------
     * 本不该用这个事务消息进行事务控制的，因为GoodsSkuServiceImpl的reduceReserve方法中进行减库存，在发送事务消息，
     * 有可能事务消息发出，但是rocketmq没接受到。（）
     * 应该在本模块的数据库再建一张表，用来存储TxMessage，然后用定时器不停扫描，发送到订单模块，
     * 订单模块接收到消息后再进行该表的删除
     * @param message
     * @param o
     * @return
     *//*

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        return RocketMQLocalTransactionState.COMMIT;
    }

    */
/**
     * 回查，对reduceReserve方法中发到订单模块的mq事务消息进行回查
     * @param message
     * @return
     *//*

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String txMessageStr = (String)message.getPayload();
        TxMessage txMessage = JSONObject.parseObject(txMessageStr, TxMessage.class);
        Map<String,Object> critire = new HashMap<>();
        critire.put("userId", txMessage.getUserId());
        critire.put("goodsId",txMessage.getGoodsId());
        List<BuyRecord> record = buyRecordMongoDao.findListByMap(critire, 1, 10, null, null, BuyRecord.class);
       //查询到有该记录则把该消息设置为可消费消息
        if(record != null && record.size()> 0 ){
            return RocketMQLocalTransactionState.COMMIT;
        }else {
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }
}
*/
