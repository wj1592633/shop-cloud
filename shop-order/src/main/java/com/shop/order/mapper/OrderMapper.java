package com.shop.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.entity.Order;
import com.shop.entity.util.PageUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wj
 */
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 根据用户id分页查询用户订单
     * @param pageUtil
     * @return
     */
    @Select("SELECT order_id, user_id, sku_id, take_time, goods_name, pay_time, pay_price, goods_num, goods_price_per, state FROM tb_order WHERE user_id = #{keyword} LIMIT #{startRecord},#{size}")
    public List<Map<String,Object>> getOrderByUserId(PageUtil<Order> pageUtil);

    /**
     * 根据用户id查询所有订单数量
     * @param userId
     * @return
     */
    @Select("SELECT count(1) FROM tb_order WHERE user_id = #{userId}")
    public Long getOrderCountByUserId(Serializable userId);

    /**
     * 根据订单id更改订单状态
     * @param orderId
     * @param expectState 期望的状态
     * @param toState 最终修改成的状态
     * @return
     */
    @Update("UPDATE tb_order set state = #{toState},pay_time = #{payTime} where order_id = #{orderId} and state = #{expectState}")
    int payOrderById(@Param("orderId") Serializable orderId, @Param("expectState") Integer expectState, @Param("toState") Integer toState, @Param("payTime")LocalDateTime payTime);

    /**
     * 根据订单id更改订单状态
     * @param orderId
     * @param expectState 期望的状态
     * @param toState 最终修改成的状态
     * @return
     */
    @Update("UPDATE tb_order set state = #{toState} where order_id = #{orderId} and state = #{expectState}")
    int changeOrderState(@Param("orderId") Serializable orderId, @Param("expectState") Integer expectState, @Param("toState") Integer toState);

}
