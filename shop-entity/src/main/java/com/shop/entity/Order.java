package com.shop.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wj
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId("order_id")
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

    /**
     * 下单时间
     */

    private LocalDateTime takeTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付价格
     */
    private BigDecimal payPrice;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品单价
     */
    private BigDecimal goodsPricePer;

    /**
     * 订单状态
     * 0-下单待付款中
     * 1-付款成功
     * 2-超时，系统取消
     * 3-用户自己取消
     */
    private Integer state;

    private Integer version;


}
