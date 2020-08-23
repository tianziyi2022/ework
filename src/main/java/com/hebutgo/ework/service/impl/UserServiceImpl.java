package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.ChangeDetailVo;
import com.hebutgo.ework.entity.vo.LoginVo;
import com.hebutgo.ework.entity.vo.LogoutVo;
import com.hebutgo.ework.entity.vo.RegisterVo;
import com.hebutgo.ework.mapper.UserMapper;
import com.hebutgo.ework.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

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
        userMapper.insert(user0);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(user0);
        User user1 = userMapper.selectOne(queryWrapper1);
        RegisterVo registerVo = new RegisterVo();
        registerVo.setId(user1.getId());
        registerVo.setUserName(user1.getUserName());
        return registerVo;
    }

    @Override
    public LoginVo login(LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();
        User users1 = new User();
        //登录名userId登陆
        users1.setUserId(loginId);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(users1);
        User user1 = userMapper.selectOne(queryWrapper1);
        //电话号phone登陆
        if (Objects.isNull(user1)) {
            users1 = new User();
            users1.setPhone(loginId);
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.setEntity(users1);
            user1 = userMapper.selectOne(queryWrapper2);
        }
        //学工号studentId/adminId登陆
        if (Objects.isNull(user1)) {
            users1 = new User();
            users1.setStudentId(loginId);
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.setEntity(users1);
            user1 = userMapper.selectOne(queryWrapper2);
        }
        if (Objects.isNull(user1)) {
            throw new BizException("该账号不存在");
        }
        else if (user1.getStatus() == 0) {
            throw new BizException("该账户已注销");
        }
        else if (user1.getStatus() == 20) {
            throw new BizException("该账户被锁定");
        }
        else if (user1.getStatus() != 10) {
            throw new BizException("该账户状态异常");
        }
        else if(!user1.getPassword().equals(password)){
            throw new BizException("密码不正确");
        }
        user1.setToken("user-token-" + UUID.randomUUID());
        user1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setEntity(users1);
        userMapper.update(user1, updateWrapper);
        LoginVo loginVo = new LoginVo();
        loginVo.setId(user1.getId());
        loginVo.setToken(user1.getToken());
        loginVo.setUserName(user1.getUserName());
        loginVo.setType(20);
        return loginVo;
    }

    @Override
    public ChangeDetailVo changeDetail(ChangeDetailRequest changeDetailRequest) {
        if(changeDetailRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(changeDetailRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!user.getToken().equals(changeDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        if(!"".equals(changeDetailRequest.getPassword())){
            user.setPassword(changeDetailRequest.getPassword());
        }
        if(!"".equals(changeDetailRequest.getPhone())){
            user.setPhone(changeDetailRequest.getPhone());
        }
        if(!"".equals(changeDetailRequest.getSchoolId())){
            user.setStudentId(changeDetailRequest.getSchoolId());
        }
        if(!"".equals(changeDetailRequest.getUserName())){
            user.setUserName(changeDetailRequest.getUserName());
        }
        //修改信息后需重新登陆
        user.setToken("");
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
        ChangeDetailVo changeDetailVo = new ChangeDetailVo();
        changeDetailVo.setId(user.getId());
        changeDetailVo.setType(20);
        changeDetailVo.setUserName(user.getUserName());
        return changeDetailVo;
    }

    @Override
    public LogoutVo quit(LogoutRequest logoutRequest) {
        if(logoutRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(logoutRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!user.getToken().equals(logoutRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        user.setToken("");
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
        LogoutVo logoutVo = new LogoutVo();
        logoutVo.setType(20);
        logoutVo.setTopic("安全退出成功！");
        return logoutVo;
    }

    @Override
    public LogoutVo logout(LogoutRequest logoutRequest) {
        if(logoutRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(logoutRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!user.getToken().equals(logoutRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        user.setToken("");
        user.setStatus(0);
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
        LogoutVo logoutVo = new LogoutVo();
        logoutVo.setType(20);
        logoutVo.setTopic("注销成功！");
        return logoutVo;
    }
}
