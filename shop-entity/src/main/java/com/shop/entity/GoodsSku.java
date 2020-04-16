package com.shop.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@TableName("tb_goods_sku")
@Builder
public class GoodsSku implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("sku_id")
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
    @TableLogic
    private Integer state;

    private Integer version;


}
