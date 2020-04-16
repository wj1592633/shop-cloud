package com.shop.api.feign.goods.service;

import com.shop.common.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("shop-goods")
public interface GoodsSkuService {
    @GetMapping("/sku/reduce")
    public ResponseResult reduceReserve(@RequestParam("skuId") String skuId);
}
