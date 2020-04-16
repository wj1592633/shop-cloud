package com.shop.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.entity.Goods;
import com.shop.entity.util.PageUtil;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wj
 */
public interface IGoodsService extends IService<Goods> {
    public PageUtil<Goods> getGoodsListByNameTtileDesc(PageUtil<Goods> pageUtil);

    public Goods getGoodsById(String id);
}
