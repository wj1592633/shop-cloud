package com.shop.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.entity.GoodsSku;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wj
 */
public interface IGoodsSkuService extends IService<GoodsSku> {
    /**
     * 根据goodsId获取所有的GoodsSku
     * @param goodsId
     * @return
     */
    List<GoodsSku>  getGoodsSkuByGoodsId(String goodsId);
}
