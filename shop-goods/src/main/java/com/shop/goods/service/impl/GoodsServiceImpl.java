package com.shop.goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.common.dto.ResponseResult;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.exception.CustomException;
import com.shop.entity.Goods;
import com.shop.entity.GoodsSku;
import com.shop.entity.util.PageUtil;
import com.shop.goods.mapper.GoodsMapper;
import com.shop.goods.redis.RedisService;
import com.shop.goods.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wj
 */
@Service
@Slf4j
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisService redisService;

    @Override
    public PageUtil<Goods> getGoodsListByNameTtileDesc(PageUtil<Goods> pageUtil) {
        //先通过缓存查询
        try {
            PageUtil<Goods> result = redisService.getGoodsListWithPage(pageUtil);
            if (result != null && result.getRecords() != null && result.getRecords().size() > 0){
                return result;
            }
        }catch (Exception e){
            log.error(this.getClass().getName() + ",getGoodsListByNameTtileDesc,redis缓存出错-->" + e.getMessage());
        }

        pageUtil.setStartRecord(((pageUtil.getCurrent() - 1) * pageUtil.getSize()));
        List<Goods> list = goodsMapper.getGoodsNSkuList(pageUtil);
        pageUtil.setTotal(goodsMapper.getGoodsNSkuListCount(pageUtil));
        pageUtil.setRecords(list);
        return pageUtil;
    }

    @Override
    public Goods getGoodsById(String id) {
        if(StringUtils.isBlank(id)){
            throw new CustomException(ExceptionEnum.NO_WORK);
        }
        Goods goods = goodsMapper.getGoodsById(id);
        return goods;
    }
}
