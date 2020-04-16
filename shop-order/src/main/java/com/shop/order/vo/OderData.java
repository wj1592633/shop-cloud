package com.shop.order.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class OderData implements Serializable {
    private String skuParams;
    private String skuId;
    private BigDecimal goodsPricePer;
    private String goodsId;
    /**
     * 用户id,商家id，订单的商品属于的商家
     */
    private String userId;
    /**
     * 商品名称
     */
    private String name;

}
