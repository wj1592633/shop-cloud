package com.shop.api.feign.order.service;

import com.shop.api.feign.order.service.fallback.OrderServiceFallback;
import com.shop.common.dto.ResponseResult;
import com.shop.entity.Order;
import com.shop.entity.util.PageUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="shop-order",fallback = OrderServiceFallback.class)
public interface OrderService {
    @GetMapping("/order/records")
    public ResponseResult getOrderByUserId(PageUtil pageUtil);

    @RequestMapping(value = "/order/detail", method = RequestMethod.GET)
    public ResponseResult getOrderByOrderId(@RequestParam("orderId") String orderId);

    @PostMapping("/order/finish")
    public ResponseResult finishOrder(String orderId);
}
