package com.shop.goods.tb.service.impl;

import com.shop.goods.tb.entity.Order;
import com.shop.goods.tb.mapper.OrderMapper;
import com.shop.goods.tb.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wj
 * @since 2019-10-12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
