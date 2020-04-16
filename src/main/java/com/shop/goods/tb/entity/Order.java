package com.shop.goods.tb.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wj
 * @since 2019-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 用户id
     */
    private String userId;

    private String skuId;

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
     */
    private Integer state;

    private Integer version;


}
