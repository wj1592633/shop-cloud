package com.shop.goods.tb.service.impl;

import com.shop.goods.tb.entity.StopRepeat;
import com.shop.goods.tb.mapper.StopRepeatMapper;
import com.shop.goods.tb.service.IStopRepeatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 下单前,防止重复秒杀表 服务实现类
 * </p>
 *
 * @author wj
 * @since 2019-10-12
 */
@Service
public class StopRepeatServiceImpl extends ServiceImpl<StopRepeatMapper, StopRepeat> implements IStopRepeatService {

}
