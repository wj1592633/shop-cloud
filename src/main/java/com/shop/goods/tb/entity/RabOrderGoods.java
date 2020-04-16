package com.shop.goods.tb.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品模块的下单rabbit辅助表
 * </p>
 *
 * @author wj
 * @since 2019-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_rab_order_goods")
public class RabOrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    private String skuId;

    private LocalDateTime orderTime;

    /**
     * 状态
     */
    private Integer state;


}
