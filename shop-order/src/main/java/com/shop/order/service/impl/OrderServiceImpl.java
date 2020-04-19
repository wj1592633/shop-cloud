package com.shop.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.common.constant.Constant;
import com.shop.common.constant.RedisConstant;
import com.shop.common.dto.ResponseResult;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.enumation.OrderStateEnum;
import com.shop.common.exception.CustomException;
import com.shop.common.script.RedisScript;
import com.shop.common.util.IdGenerator;
import com.shop.common.util.ToolUtils;
import com.shop.entity.Order;
import com.shop.entity.util.PageUtil;
import com.shop.order.mapper.OrderMapper;
import com.shop.order.service.IOrderService;
import com.shop.order.thread.ThreadPoolHolder;
import com.shop.order.util.SecurityKit;
import com.shop.order.vo.OderData;
import com.shop.order.wrapper.OrderWrapper;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wj
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    RedisTemplate<String, Serializable> redisTemplate;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    TransactionTemplate transactionTemplate;
    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Override
    public ResponseResult addOrder(OderData data) {
        //开始应该作幂等性处理的，先暂时将就着。 todo
        //或者添加订单时不做幂等处理，不过模拟支付订单，就必须要作幂等处理了
        //sku数据在redis中的hash的key
        String qkey = RedisConstant.GOODS_SKU_PRE + data.getGoodsId() + ":" + data.getSkuId();
        List<String> strings = Arrays.asList(qkey);
        DefaultRedisScript<List> redisScript = new DefaultRedisScript<List>();
        //Long\Boolean\list\DeSerialized之一。写Object的话，返回是默认List
        redisScript.setResultType(List.class);
        //加载lua脚本
        redisScript.setScriptText(RedisScript.REDUCE_SKU_RESERVE);
        List execute = redisTemplate.execute(redisScript, strings, 0);
        if (execute == null) {
            throw new CustomException(ExceptionEnum.UNKNOWN_EXCEPTION);
        }
        Long resultCode = (long) execute.get(0);
        //-1表示商品没有库存
        if (resultCode < 0) {
            return ResponseResult.fail("该商品已经售光");
        }
        String orderId = IdGenerator.OrderIdGenarate(data.getUserId());
        transactionTemplate.execute((status) -> {
            int insert = 0;

            try {
                //当前用户id
                String currentUser = SecurityKit.getUserId();
                Order order = Order.builder().orderId(orderId).takeTime(LocalDateTime.now())
                        .userId(currentUser).skuId(data.getSkuId()).goodsPricePer(data.getGoodsPricePer())
                        .goodsNum(1).goodsName(data.getName()).payPrice(data.getGoodsPricePer())
                        .payTime(null).state(OrderStateEnum.NEED_PAY.getStateNum()).version(1)
                        .build();
                insert = orderMapper.insert(order);
            } catch (Exception e) {
                //插入订单数据出错的话，redis的sku库存+1
                redisTemplate.boundHashOps(qkey).increment(RedisConstant.SKU_RESERVE, 1);
                throw e;
            }
            if (insert < 1) {
                //插入订单数据出错的话，redis的sku库存+1
                redisTemplate.boundHashOps(qkey).increment(RedisConstant.SKU_RESERVE, 1);
                throw new CustomException(ExceptionEnum.HAD_BOUGHT);
            }
            return null;
        });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constant.TIME_OUT_ITEM_REDIS_KEY, qkey);
        jsonObject.put(Constant.TIME_OUT_ITEM_DB_SKU_KEY, orderId);
        //发送订单延时消息，为测试方便，订单设为1min后超时
        ThreadPoolHolder.doSentOrderTimeoutMsgTask(jsonObject);
        return ResponseResult.ok("下单成功");
    }

    @Override
    public PageUtil<Order> getOrderByUserId(PageUtil<Order> pageUtil) {
        String userId = SecurityKit.getUserId();
        //用keyword代替id进行查询
        pageUtil.setKeyword(userId);
        List<Map<String, Object>> orders = orderMapper.getOrderByUserId(pageUtil);
        pageUtil.setTotal(orderMapper.getOrderCountByUserId(userId));
        OrderWrapper wrapper = new OrderWrapper(orders);
        pageUtil.setRecords((List) wrapper.wrap());
        return pageUtil;
    }

    @Override
    @Transactional
    public int payOrderById(Serializable orderId, Integer expectState, Integer toState) {
        return orderMapper.payOrderById(orderId, expectState, toState, LocalDateTime.now());
    }

    @Override
    public int handleOrderTimeOut(String orderId) {
        return orderMapper.changeOrderState(orderId, OrderStateEnum.NEED_PAY.getStateNum(), OrderStateEnum.CANCEL_TIME_OUT.getStateNum());
    }
}
