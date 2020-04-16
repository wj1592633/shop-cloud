package com.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.entity.GoodsSku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wj
 */
public interface GoodsSkuMapper extends BaseMapper<GoodsSku> {
    @Update("UPDATE tb_goods_sku set sku_reserve = sku_reserve - 1 , version = version + 1  WHERE sku_id = #{skuId} and version = #{version} and state = 1 and sku_reserve > 0  ")
    public int reduceReserve(@Param("skuId") String skuId,@Param("version") Integer version);

    @Select("SELECT version FROM tb_goods_sku WHERE sku_id = #{skuId}")
    public Integer getVersionById(String skuId);
}
