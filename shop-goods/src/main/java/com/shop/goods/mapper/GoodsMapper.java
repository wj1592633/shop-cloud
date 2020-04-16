package com.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.entity.Goods;
import com.shop.entity.util.PageUtil;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wj
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 分页有条件地查询多条记录
     * @param pageUtil
     * @return
     */
    public List<Goods> getGoodsNSkuList(PageUtil<Goods> pageUtil);

    public long getGoodsNSkuListCount(PageUtil<Goods> pageUtil);

    /**
     * 根据id查询单挑记录
     * @param id
     * @return
     */
    public Goods getGoodsById(String id);

    /**
     * 查询所有状态为1的记录
     * @return
     */
    public List<Goods> getALLGoodsNSku();
}
