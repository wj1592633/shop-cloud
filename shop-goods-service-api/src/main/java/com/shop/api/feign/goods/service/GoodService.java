package com.shop.api.feign.goods.service;

import com.shop.common.dto.ResponseResult;
import com.shop.entity.Goods;
import com.shop.entity.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient("shop-goods")
public interface GoodService {

    @GetMapping("/goods/list")
    public ResponseResult getGoodsNSkuList(@RequestParam("pageUtil") PageUtil<Goods> pageUtil);
}
