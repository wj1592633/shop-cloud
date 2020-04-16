package com.shop.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 用户表，简单处理，不分卖主和买主 Mapper 接口
 * </p>
 *
 * @author wj
 */
public interface UserMapper extends BaseMapper<User> {
    @Update("UPDATE tb_user SET amount = #{amount},version = version + 1 where user_id = #{userId} and version = #{version}")
    public abstract int updateAmount(@Param("userId") Serializable userId, @Param("amount") BigDecimal amount, @Param("version") Integer version);

    @Select("select amount from tb_user where user_id = #{userId}")
    public BigDecimal getAmountByUserId(String userId);
}
