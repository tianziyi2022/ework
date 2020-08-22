package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.AdminRegisterRequest;
import com.hebutgo.ework.entity.request.UserRegisterRequest;
import com.hebutgo.ework.entity.vo.RegisterVo;
import com.hebutgo.ework.mapper.UserMapper;
import com.hebutgo.ework.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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

    @Resource
    UserMapper userMapper;

    @Override
    public RegisterVo register(UserRegisterRequest userRegisterRequest) {
        User user0 = new User();
        user0.setUserId(userRegisterRequest.getUserId());
        user0.setStatus(10);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(user0);
        User user = userMapper.selectOne(queryWrapper);
        if (!Objects.isNull(user)) {
            throw new BizException("该用户名已被注册");
        }
        user0 = new User();
        user0.setPhone(userRegisterRequest.getPhone());
        user0.setStatus(10);
        queryWrapper.setEntity(user0);
        user = userMapper.selectOne(queryWrapper);
        if (!Objects.isNull(user)) {
            throw new BizException("该电话号码已注册");
        }
        user0 = new User();
        user0.setStudentId(userRegisterRequest.getStudentId());
        user0.setStatus(10);
        queryWrapper.setEntity(user0);
        user = userMapper.selectOne(queryWrapper);
        if (!Objects.isNull(user)) {
            throw new BizException("该学号已注册");
        }
        user0 = new User();
        user0.setUserId(userRegisterRequest.getUserId());
        user0.setStudentId(userRegisterRequest.getStudentId());
        user0.setPassword(userRegisterRequest.getPassword());
        user0.setPhone(userRegisterRequest.getPhone());
        user0.setUserName(userRegisterRequest.getUserName());
        user0.setStatus(10);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setEntity(user0);
        userMapper.insert(user0);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(user0);
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper1.setEntity(user0);
        User user1 = userMapper.selectOne(queryWrapper1);
        RegisterVo registerVo = new RegisterVo();
        registerVo.setId(user1.getId());
        registerVo.setUserName(user1.getUserName());
        return registerVo;
    }
}
