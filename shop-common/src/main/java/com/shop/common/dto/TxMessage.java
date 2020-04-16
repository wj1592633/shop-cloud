package com.shop.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 实体用于：下单时，商品模块产生的数据，通过rocketmq传到订单模块，完成订单的生成
 */
@Data
public class TxMessage implements Serializable {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品skuId
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String goodsName;

    private String goodsId;
    /**
     * 下单时间
     */
    private LocalDateTime takeTime;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品单价
     */
    private BigDecimal goodsPricePer;


}
