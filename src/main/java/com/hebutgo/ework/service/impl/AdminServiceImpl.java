package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.GroupAdmin;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.*;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.mapper.GroupAdminMapper;
import com.hebutgo.ework.mapper.GroupInfoMapper;
import com.hebutgo.ework.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.tomcat.jni.Time;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
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

    @Resource
    GroupInfoMapper groupInfoMapper;

    @Resource
    GroupAdminMapper groupAdminMapper;

    @Override
    public RegisterVo register(AdminRegisterRequest adminRegisterRequest) {
        LambdaQueryWrapper<Admin> query = Wrappers.lambdaQuery(Admin.class);
        Admin admin0 = new Admin();
        admin0.setUserId(adminRegisterRequest.getUserId());
        Admin admin = adminMapper.selectOne(
                query.eq(Admin::getUserId,adminRegisterRequest.getUserId())
        );
        if (!Objects.isNull(admin)){
            if(admin.getStatus().equals(10)) {
                throw new BizException("该用户名已被注册");
            }else{
                admin.setUserId("admin-unvalid-userId-" + UUID.randomUUID());
                adminMapper.updateById(admin);
            }
        }
        admin = adminMapper.selectOne(
                query.eq(Admin::getPhone,adminRegisterRequest.getPhone())
        );
        if (!Objects.isNull(admin)) {
            if(admin.getStatus().equals(10)) {
                throw new BizException("该电话号码已注册");
            }else{
                admin.setPhone("admin-unvalid-phone-" + UUID.randomUUID());
                adminMapper.updateById(admin);
            }
        }
        admin = adminMapper.selectOne(
                query.eq(Admin::getAdminId,adminRegisterRequest.getAdminId())
        );
        if (!Objects.isNull(admin)) {
            if(admin.getStatus().equals(10)) {
                throw new BizException("该学工号已注册");
            }else{
                admin.setAdminId("admin-unvalid-adminId-" + UUID.randomUUID());
                adminMapper.updateById(admin);
            }
        }
        admin0 = new Admin();
        BeanUtils.copyProperties(adminRegisterRequest,admin);
        admin0.setStatus(10);
        adminMapper.insert(admin0);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.setEntity(admin0);
        Admin admin1 = adminMapper.selectOne(
                query.eq(Admin::getUserId,adminRegisterRequest.getUserId())
        );
        RegisterVo registerVo = new RegisterVo();
        BeanUtils.copyProperties(admin1,registerVo);
        return registerVo;
    }

    @Override
    public LoginVo login(LoginRequest loginRequest) {
        LambdaQueryWrapper<Admin> query = Wrappers.lambdaQuery(Admin.class);
        //登录名userId登陆
        Admin admin1 = adminMapper.selectOne(
                query.eq(Admin::getUserId,loginRequest.getLoginId())
        );
        //电话号phone登陆
        if (Objects.isNull(admin1)) {
            admin1 = adminMapper.selectOne(
                    query.eq(Admin::getPhone,loginRequest.getLoginId())
            );
        }
        //学工号studentId/AdminId登陆
        if (Objects.isNull(admin1)) {
            admin1 = adminMapper.selectOne(
                    query.eq(Admin::getAdminId,loginRequest.getLoginId())
            );
        }
        if (Objects.isNull(admin1)) {
            throw new BizException("该账号不存在");
        }
        if (admin1.getStatus() == 0) {
            throw new BizException("该账户已注销");
        }
        if (admin1.getStatus() == 20) {
            throw new BizException("该账户被锁定");
        }
        if (admin1.getStatus() != 10) {
            throw new BizException("该账户状态异常");
        }
        if(!admin1.getPassword().equals(loginRequest.getPassword())){
            throw new BizException("密码不正确");
        }
        admin1.setToken("admin-token-" + UUID.randomUUID());
        admin1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        adminMapper.updateById(admin1);
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyProperties(admin1,loginVo);
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
        if(!Objects.equals(admin.getToken(), changeDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        BeanUtils.copyProperties(changeDetailRequest,admin);
        //修改信息后需重新登陆
        admin.setToken("");
        //更新时间
        admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        adminMapper.updateById(admin);
        ChangeDetailVo changeDetailVo = new ChangeDetailVo();
        BeanUtils.copyProperties(admin,changeDetailVo);
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
        if(!Objects.equals(admin.getToken(), logoutRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        admin.setToken("");
        admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
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
        if(!Objects.equals(admin.getToken(), logoutRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        admin.setToken("");
        admin.setStatus(0);
        admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        adminMapper.updateById(admin);
        LogoutVo logoutVo = new LogoutVo();
        logoutVo.setType(10);
        logoutVo.setTopic("注销成功！");
        return logoutVo;
    }

    @Override
    public AdminDetailVo detail(AccountDetailRequest accountDetailRequest) {
        if(accountDetailRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(accountDetailRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), accountDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        // TODO: 2020/9/6 修改返回类，拆分为两个请求
        AdminDetailVo adminDetailVo = new AdminDetailVo();
        adminDetailVo.setAdminId(admin.getAdminId());
        String phone = admin.getPhone().substring(0,3)+"****"+admin.getPhone().substring(7);
        adminDetailVo.setPhone(phone);
        adminDetailVo.setUserName(admin.getUserName());
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setCreateAdmin(admin.getId());
        QueryWrapper<GroupInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(groupInfo);
        List<GroupInfo> createGroupInfoList = groupInfoMapper.selectList(queryWrapper);
        List<GroupDetailVo> createGroupDetailVoList = new ArrayList<>();
        for(GroupInfo group:createGroupInfoList){
            if(group.getStatus()!=100) {
                GroupDetailVo groupDetailVo = new GroupDetailVo();
                groupDetailVo.setId(group.getId());
                groupDetailVo.setCreateAdminName(admin.getUserName());
                groupDetailVo.setDescriptions(group.getDescriptions());
                groupDetailVo.setGroupCode(group.getGroupCode());
                groupDetailVo.setGroupName(group.getGroupName());
                groupDetailVo.setStatus(group.getStatus());
                createGroupDetailVoList.add(groupDetailVo);
            }
        }
        adminDetailVo.setCreateGroupCount(createGroupDetailVoList.size());
        adminDetailVo.setCreateGroupDetailVoList(createGroupDetailVoList);
        GroupAdmin groupAdmin0 = new GroupAdmin();
        groupAdmin0.setAdminId(admin.getId());
        groupAdmin0.setIsDelete(0);
        QueryWrapper<GroupAdmin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.setEntity(groupAdmin0);
        List<GroupAdmin> groupAdminList = groupAdminMapper.selectList(queryWrapper1);
        List<GroupDetailVo> adminGroupDetailVoList = new ArrayList<>();
        for(GroupAdmin groupAdmin:groupAdminList){
            GroupInfo group = groupInfoMapper.selectById(groupAdmin.getGroupId());
            GroupDetailVo groupDetailVo = new GroupDetailVo();
            groupDetailVo.setId(group.getId());
            groupDetailVo.setCreateAdminName(adminMapper.selectById(group.getCreateAdmin()).getUserName());
            groupDetailVo.setDescriptions(group.getDescriptions());
            groupDetailVo.setGroupCode(group.getGroupCode());
            groupDetailVo.setGroupName(group.getGroupName());
            groupDetailVo.setStatus(group.getStatus());
            adminGroupDetailVoList.add(groupDetailVo);
        }
        adminDetailVo.setAdminGroupCount(adminGroupDetailVoList.size());
        adminDetailVo.setAdminGroupDetailVoList(adminGroupDetailVoList);
        return adminDetailVo;
    }

    @Override
    public List<WorkGroupVo> groupList(AccountDetailRequest accountDetailRequest) {
        // TODO: 2020/9/6 修改请求 
        if(accountDetailRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(accountDetailRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), accountDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        List<WorkGroupVo> workGroupVoList = new ArrayList<>();
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setCreateAdmin(admin.getId());
        QueryWrapper<GroupInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(groupInfo);
        List<GroupInfo> createGroupInfoList = groupInfoMapper.selectList(queryWrapper);
        for(GroupInfo groupInfo1 : createGroupInfoList){
            workGroupVoList.add(new WorkGroupVo(groupInfo1));
        }
        GroupAdmin groupAdmin0 = new GroupAdmin();
        groupAdmin0.setAdminId(admin.getId());
        groupAdmin0.setIsDelete(0);
        QueryWrapper<GroupAdmin> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.setEntity(groupAdmin0);
        List<GroupAdmin> groupAdminList = groupAdminMapper.selectList(queryWrapper1);
        for(GroupAdmin groupAdmin1 :groupAdminList){
            GroupInfo groupInfo1 = groupInfoMapper.selectById(groupAdmin1.getGroupId());
            workGroupVoList.add(new WorkGroupVo(groupInfo1));
        }
        return workGroupVoList;
    }
}
