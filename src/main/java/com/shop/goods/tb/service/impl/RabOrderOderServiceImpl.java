package com.shop.goods.tb.service.impl;

import com.shop.goods.tb.entity.RabOrderOder;
import com.shop.goods.tb.mapper.RabOrderOderMapper;
import com.shop.goods.tb.service.IRabOrderOderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单模块的下单rabbit辅助表，防重 服务实现类
 * </p>
 *
 * @author wj
 * @since 2019-10-12
 */
@Service
public class RabOrderOderServiceImpl extends ServiceImpl<RabOrderOderMapper, RabOrderOder> implements IRabOrderOderService {

}
