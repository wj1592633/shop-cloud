package com.shop.goods.tb.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_goods_sku")
public class GoodsSku implements Serializable {

    private static final long serialVersionUID = 1L;

    private String skuId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 规格参数
     */
    private String skuParams;

    /**
     * 目前库存
     */
    private Integer skuReserve;

    /**
     * 开始库存
     */
    private Integer skuStartReserve;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品图片
     */
    private String picture;

    /**
     * 状态
     */
    private Integer state;

    private Integer version;


}
