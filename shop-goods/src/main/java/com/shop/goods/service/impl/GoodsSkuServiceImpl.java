package com.shop.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.common.constant.Constant;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.exception.CustomException;
import com.shop.entity.Goods;
import com.shop.entity.GoodsSku;
import com.shop.goods.mapper.GoodsMapper;
import com.shop.goods.mapper.GoodsSkuMapper;
import com.shop.goods.redis.RedisService;
import com.shop.goods.service.IGoodsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements IGoodsSkuService {
    @Autowired
    GoodsSkuMapper skuMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    RedisService redisService;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Override
    public List<GoodsSku> getGoodsSkuByGoodsId(String goodsId) {
        if (StringUtils.isBlank(goodsId)){
            throw new CustomException(ExceptionEnum.NULL_ARGS);
        }
        //缓存查询数据
        try {
            List<GoodsSku> result = redisService.getGoodsSkuByGoodsId(goodsId);
            if (result != null && result.size() > 0){
                return result;
            }
        }catch (Exception e){
           log.error(this.getClass().getName() + ",getGoodsSkuByGoodsId,redis缓存出错-->" + e.getMessage());
        }

        Goods goods = goodsMapper.selectById(goodsId);
        if (null == goods){
            return null;
        }
        LocalDateTime startTime = goods.getStartTime();
        LocalDateTime endTime = goods.getEndTime();
        LocalDateTime now = LocalDateTime.now();
        //必须在商品开卖时间内
        if (now.isBefore(endTime) && now.isAfter(startTime)) {
            QueryWrapper<GoodsSku> wrapper = new QueryWrapper<>();
            wrapper.eq("goods_id", goodsId);
            wrapper.eq("state", Constant.STATE_NORMAL);
            List<GoodsSku> list = list(wrapper);
            return list;
        }
        return null;
    }
}
