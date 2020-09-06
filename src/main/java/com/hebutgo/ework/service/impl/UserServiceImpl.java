package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.*;
import com.hebutgo.ework.mapper.GroupInfoMapper;
import com.hebutgo.ework.mapper.UserMapper;
import com.hebutgo.ework.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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

    @Resource
    GroupInfoMapper groupInfoMapper;

    @Override
    public RegisterVo register(UserRegisterRequest userRegisterRequest) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery(User.class);
        User user = userMapper.selectOne(
                query.eq(User::getUserId,userRegisterRequest.getUserId())
        );
        if (!Objects.isNull(user)) {
            if(user.getStatus().equals(10)) {
                throw new BizException("该用户名已被注册");
            }else{
                user.setUserId("user-unvalid-userId-" + UUID.randomUUID());
                userMapper.updateById(user);
            }
        }
        user = userMapper.selectOne(
                query.eq(User::getPhone,userRegisterRequest.getPhone())
        );
        if (!Objects.isNull(user)) {
            if(user.getStatus().equals(10)) {
                throw new BizException("该电话号码已注册");
            }else{
                user.setPhone("user-unvalid-phone-" + UUID.randomUUID());
                userMapper.updateById(user);
            }
        }
        user = userMapper.selectOne(
                query.eq(User::getStudentId,userRegisterRequest.getStudentId())
        );
        if (!Objects.isNull(user)) {
            if(user.getStatus().equals(10)) {
                throw new BizException("该学号已注册");
            }else{
                user.setStudentId("user-unvalid-studentId-" + UUID.randomUUID());
                userMapper.updateById(user);
            }
        }
        User user0 = new User();
        BeanUtils.copyProperties(userRegisterRequest,user0);
        user0.setStatus(10);
        userMapper.insert(user0);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(user0);
        User user1 = userMapper.selectOne(queryWrapper1);
        RegisterVo registerVo = new RegisterVo();
        BeanUtils.copyProperties(user1,registerVo);
        return registerVo;
    }

    @Override
    public LoginVo login(LoginRequest loginRequest) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery(User.class);
        //登录名userId登陆
        User user1 = userMapper.selectOne(
                query.eq(User::getUserId,loginRequest.getLoginId())
        );
        //电话号phone登陆
        if (Objects.isNull(user1)) {
            user1 = userMapper.selectOne(
                    query.eq(User::getPhone,loginRequest.getLoginId())
            );
        }
        //学工号studentId/adminId登陆
        if (Objects.isNull(user1)) {
            user1 = userMapper.selectOne(
                    query.eq(User::getStudentId,loginRequest.getLoginId())
            );
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
        else if(!user1.getPassword().equals(loginRequest.getPassword())){
            throw new BizException("密码不正确");
        }
        user1.setToken("user-token-" + UUID.randomUUID());
        user1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setEntity(user1);
        userMapper.update(user1, updateWrapper);
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(user1,loginVo);
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
        if(!Objects.equals(user.getToken(), changeDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        BeanUtils.copyProperties(changeDetailRequest,user);
        //修改信息后需重新登陆
        user.setToken("");
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
        ChangeDetailVo changeDetailVo = new ChangeDetailVo();
        BeanUtils.copyProperties(user,changeDetailVo);
        changeDetailVo.setType(20);;
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
        if(!Objects.equals(user.getToken(), logoutRequest.getToken())){
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
        if(!Objects.equals(user.getToken(), logoutRequest.getToken())){
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

    @Override
    public JoinGroupVo joinGroup(JoinGroupRequest joinGroupRequest) {
        if(joinGroupRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(joinGroupRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), joinGroupRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        if(!Objects.isNull(user.getGroupId())){
            throw new BizException("用户已在小组中");
        }
        GroupInfo group = groupInfoMapper.selectById(joinGroupRequest.getGroupId());
        if(Objects.isNull(group)||(group.getStatus()!=30&&group.getStatus()!=40)){
            throw new BizException("小组不存在或未开放加入");
        }
        user.setGroupId(joinGroupRequest.getGroupId());
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
        JoinGroupVo joinGroupVo = new JoinGroupVo();
        BeanUtils.copyProperties(group,joinGroupVo);
        joinGroupVo.setTopic("成功加入小组");
        return joinGroupVo;
    }

    @Override
    public JoinGroupVo quitGroup(JoinGroupRequest joinGroupRequest) {
        if(joinGroupRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(joinGroupRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), joinGroupRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        if(Objects.isNull(user.getGroupId())){
            throw new BizException("用户未加入小组");
        }
        GroupInfo group = groupInfoMapper.selectById(user.getGroupId());
        if(Objects.isNull(group)||(group.getStatus()!=30&&group.getStatus()!=40)){
            throw new BizException("小组不存在或未开放退出");
        }
        user.setGroupId(null);
        user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(user);
        JoinGroupVo joinGroupVo = new JoinGroupVo();
        BeanUtils.copyProperties(group,joinGroupVo);
        joinGroupVo.setTopic("成功退出小组");
        return joinGroupVo;
    }

    @Override
    public UserDetailVo detail(AccountDetailRequest accountDetailRequest) {
        if(accountDetailRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(accountDetailRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), accountDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        UserDetailVo userDetailVo = new UserDetailVo();
        BeanUtils.copyProperties(user,userDetailVo);
        if(Objects.isNull(user.getGroupId())){
            userDetailVo.setGroupCode("-");
            userDetailVo.setGroupId(0);
            userDetailVo.setGroupName("未加入小组");
        } else {
            GroupInfo group = groupInfoMapper.selectById(user.getGroupId());
            BeanUtils.copyProperties(group,userDetailVo);
            userDetailVo.setGroupId(user.getGroupId());
        }
        String phone = user.getPhone().substring(0,3)+"****"+user.getPhone().substring(7);
        userDetailVo.setPhone(phone);
        return userDetailVo;
    }
}
