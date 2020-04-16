package com.shop.api.feign.order.service.fallback;

import com.shop.api.feign.order.service.OrderService;
import com.shop.common.dto.ResponseResult;
import com.shop.entity.util.PageUtil;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFallback implements OrderService{
    private ResponseResult grobal(){
        return ResponseResult.fail("请重试");
    }
    @Override
    public ResponseResult getOrderByUserId(PageUtil pageUtil) {
        return ResponseResult.fail("请重试");
    }

    @Override
    public ResponseResult getOrderByOrderId(String orderId) {
        return ResponseResult.fail("请重试");
    }

    @Override
    public ResponseResult finishOrder(String orderId) {
        return grobal();
    }
}
