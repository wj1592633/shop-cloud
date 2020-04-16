package com.shop.goods.controller;


import com.shop.common.constant.RedisConstant;
import com.shop.common.dto.ResponseResult;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.exception.CustomException;
import com.shop.common.script.RedisScript;
import com.shop.common.util.ToolUtils;
import com.shop.entity.Goods;
import com.shop.entity.util.PageUtil;
import com.shop.goods.service.IGoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wj
 */
@RestController
public class GoodsController {
    @Autowired
    IGoodsService goodsService;

    @Autowired
    RedisTemplate<String,Serializable> redisTemplate;

    @GetMapping("/goods/list")
    public ResponseResult getGoodsNSkuList(PageUtil<Goods> pageUtil){
        pageUtil = goodsService.getGoodsListByNameTtileDesc(pageUtil);
        return ResponseResult.ok(pageUtil);
    }
    @GetMapping("/goods/get/{id}")
    public ResponseResult getGoodsById(@PathVariable("id") String id){
        if(StringUtils.isBlank(id)){
           return ResponseResult.fail("商品编号不能为空！");
        }
        Goods goods = goodsService.getGoodsById(id);
        if(goods == null){
            return ResponseResult.fail("查无此商品！");
        }
        return ResponseResult.ok(goods);
    }



}
