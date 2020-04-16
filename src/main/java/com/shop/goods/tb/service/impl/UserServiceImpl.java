package com.shop.goods.tb.service.impl;

import com.shop.goods.tb.entity.User;
import com.shop.goods.tb.mapper.UserMapper;
import com.shop.goods.tb.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表，简单处理，不分卖主和买主 服务实现类
 * </p>
 *
 * @author wj
 * @since 2019-10-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
