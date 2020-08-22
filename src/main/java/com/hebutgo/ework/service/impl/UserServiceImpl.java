package com.hebutgo.ework.service.impl;

import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.mapper.UserMapper;
import com.hebutgo.ework.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
