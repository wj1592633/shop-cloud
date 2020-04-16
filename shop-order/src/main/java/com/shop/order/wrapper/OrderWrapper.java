package com.shop.order.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.common.enumation.OrderStateEnum;

import java.util.List;
import java.util.Map;

public class OrderWrapper extends BaseWrapper {
    public OrderWrapper(IPage<Map<String, Object>> page) {
        super(page);
    }

    public OrderWrapper(Map<String, Object> single) {
        super(single);
    }

    public OrderWrapper(List<Map<String, Object>> mutil) {
        super(mutil);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> entity) {
        entity.put("stateStr", OrderStateEnum.getStateStrByStateNum((Integer) entity.get("state")));
    }
}
