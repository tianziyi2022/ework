package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.AdminRegisterRequest;
import com.hebutgo.ework.entity.request.ChangeDetailRequest;
import com.hebutgo.ework.entity.request.LoginRequest;
import com.hebutgo.ework.entity.request.LogoutRequest;
import com.hebutgo.ework.entity.vo.ChangeDetailVo;
import com.hebutgo.ework.entity.vo.LoginVo;
import com.hebutgo.ework.entity.vo.LogoutVo;
import com.hebutgo.ework.entity.vo.RegisterVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.jni.Time;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 管理员（教师）表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    AdminMapper adminMapper;

    @Override
    public RegisterVo register(AdminRegisterRequest adminRegisterRequest) {
        Admin admin0 = new Admin();
        admin0.setUserId(adminRegisterRequest.getUserId());
        admin0.setStatus(10);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(admin0);
        Admin admin = adminMapper.selectOne(queryWrapper);
        if (!Objects.isNull(admin)) {
            throw new BizException("该用户名已被注册");
        }
        admin0 = new Admin();
        admin0.setPhone(adminRegisterRequest.getPhone());
        admin0.setStatus(10);
        queryWrapper.setEntity(admin0);
        admin = adminMapper.selectOne(queryWrapper);
        if (!Objects.isNull(admin)) {
            throw new BizException("该电话号码已注册");
        }
        admin0 = new Admin();
        admin0.setAdminId(adminRegisterRequest.getAdminId());
        admin0.setStatus(10);
        queryWrapper.setEntity(admin0);
        admin = adminMapper.selectOne(queryWrapper);
        if (!Objects.isNull(admin)) {
            throw new BizException("该学工号已注册");
        }
        admin0 = new Admin();
        admin0.setUserId(adminRegisterRequest.getUserId());
        admin0.setAdminId(adminRegisterRequest.getAdminId());
        admin0.setPassword(adminRegisterRequest.getPassword());
        admin0.setPhone(adminRegisterRequest.getPhone());
        admin0.setUserName(adminRegisterRequest.getUserName());
        admin0.setStatus(10);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setEntity(admin0);
        adminMapper.insert(admin0);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(admin0);
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper1.setEntity(admin0);
        Admin admin1 = adminMapper.selectOne(queryWrapper1);
        RegisterVo registerVo = new RegisterVo();
        registerVo.setId(admin1.getId());
        registerVo.setUserName(admin1.getUserName());
        return registerVo;
    }

    @Override
    public LoginVo login(LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        String password = loginRequest.getPassword();
        Admin admins1 = new Admin();
        //登录名userId登陆
        admins1.setUserId(loginId);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(admins1);
        Admin admin1 = adminMapper.selectOne(queryWrapper1);
        //电话号phone登陆
        if (Objects.isNull(admin1)) {
            admins1 = new Admin();
            admins1.setPhone(loginId);
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.setEntity(admins1);
            admin1 = adminMapper.selectOne(queryWrapper2);
        }
        //学工号studentId/AdminId登陆
        if (Objects.isNull(admin1)) {
            admins1 = new Admin();
            admins1.setAdminId(loginId);
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.setEntity(admins1);
            admin1 = adminMapper.selectOne(queryWrapper2);
        }
        if (Objects.isNull(admin1)) {
            throw new BizException("该账号不存在");
        }
        else if (admin1.getStatus() == 0) {
            throw new BizException("该账户已注销");
        }
        else if (admin1.getStatus() == 20) {
            throw new BizException("该账户被锁定");
        }
        else if (admin1.getStatus() != 10) {
            throw new BizException("该账户状态异常");
        }
        else if(!admin1.getPassword().equals(password)){
            throw new BizException("密码不正确");
        }
        admin1.setToken("admin-token-" + UUID.randomUUID());
        admin1.setUpdateTime(Timestamp.valueOf(LocalDateTime.now().toString().replace('T',' ')));
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.setEntity(admins1);
        adminMapper.update(admin1, updateWrapper);
        LoginVo loginVo = new LoginVo();
        loginVo.setId(admin1.getId());
        loginVo.setToken(admin1.getToken());
        loginVo.setUserName(admin1.getUserName());
        loginVo.setType(10);
        return loginVo;
    }


    @Override
    public ChangeDetailVo changeDetail(ChangeDetailRequest changeDetailRequest) {
        if(changeDetailRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(changeDetailRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!admin.getToken().equals(changeDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        if(!"".equals(changeDetailRequest.getPassword())){
            admin.setPassword(changeDetailRequest.getPassword());
        }
        if(!"".equals(changeDetailRequest.getPhone())){
            admin.setPhone(changeDetailRequest.getPhone());
        }
        if(!"".equals(changeDetailRequest.getSchoolId())){
            admin.setAdminId(changeDetailRequest.getSchoolId());
        }
        if(!"".equals(changeDetailRequest.getUserName())){
            admin.setUserName(changeDetailRequest.getUserName());
        }
        //修改信息后需重新登陆
        admin.setToken("");
        //更新时间
        admin.setUpdateTime(Timestamp.valueOf(LocalDateTime.now().toString().replace('T',' ')));
        adminMapper.updateById(admin);
        ChangeDetailVo changeDetailVo = new ChangeDetailVo();
        changeDetailVo.setId(admin.getId());
        changeDetailVo.setType(10);
        changeDetailVo.setUserName(admin.getUserName());
        return changeDetailVo;
    }

    @Override
    public LogoutVo quit(LogoutRequest logoutRequest) {
        if(logoutRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(logoutRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!admin.getToken().equals(logoutRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        admin.setToken("");
        admin.setUpdateTime(Timestamp.valueOf(LocalDateTime.now().toString().replace('T',' ')));
        adminMapper.updateById(admin);
        LogoutVo logoutVo = new LogoutVo();
        logoutVo.setType(10);
        logoutVo.setTopic("安全退出成功！");
        return logoutVo;
    }

    @Override
    public LogoutVo logout(LogoutRequest logoutRequest) {
        if(logoutRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(logoutRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!admin.getToken().equals(logoutRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        admin.setToken("");
        admin.setStatus(0);
        admin.setUpdateTime(Timestamp.valueOf(LocalDateTime.now().toString().replace('T',' ')));
        adminMapper.updateById(admin);
        LogoutVo logoutVo = new LogoutVo();
        logoutVo.setType(10);
        logoutVo.setTopic("注销成功！");
        return logoutVo;
    }

}
