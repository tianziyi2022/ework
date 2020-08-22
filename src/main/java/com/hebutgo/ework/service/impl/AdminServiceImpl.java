package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.AdminRegisterRequest;
import com.hebutgo.ework.entity.request.LoginRequest;
import com.hebutgo.ework.entity.vo.LoginVo;
import com.hebutgo.ework.entity.vo.RegisterVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
