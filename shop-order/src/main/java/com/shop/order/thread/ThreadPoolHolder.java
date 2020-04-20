package com.shop.order.thread;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.constant.Constant;
import com.shop.common.web.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolHolder {

    public static void doSentOrderTimeoutMsgTask(JSONObject data){
        innerClazz.threadPool.execute(() -> {
            try {
                RocketMQTemplate rocketMQTemplate = SpringContextHolder.getBean(RocketMQTemplate.class);
                //发送订单延时消息，为测试方便，订单设为1min后超时
                //发送延时消息.rocketmq时间固定，delayLevel  1对应1s，5对应1m
                //delayLevel -> 1s,5s,10s,30s,1m,2m,3m,4m,5m,6m,7m,8m,9m,10m,20m,30m,1h,2h
                SendResult sendResult = rocketMQTemplate.syncSend(Constant.TXMSG_TOPIC_ORDER_TIME_OUT, MessageBuilder.withPayload(data.toString()).build(), 3000, 5);
                SendStatus sendStatus = sendResult.getSendStatus();
            }catch (Exception e){
               log.error("doSentOrderTimeoutMsgTask出错," + e.getMessage());
            }

        });
    }

    private static class innerClazz{
        private static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(50000);
        public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,
                Runtime.getRuntime().availableProcessors() * 2,
                5, TimeUnit.MINUTES, queue);
    }
}
