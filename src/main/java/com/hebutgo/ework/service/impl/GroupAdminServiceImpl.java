package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.GroupAdmin;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.request.JoinGroupRequest;
import com.hebutgo.ework.entity.vo.JoinGroupVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.mapper.GroupAdminMapper;
import com.hebutgo.ework.mapper.GroupInfoMapper;
import com.hebutgo.ework.service.IGroupAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 小组的管理员表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class GroupAdminServiceImpl extends ServiceImpl<GroupAdminMapper, GroupAdmin> implements IGroupAdminService {

    @Resource
    GroupAdminMapper groupAdminMapper;

    @Resource
    GroupInfoMapper groupInfoMapper;

    @Resource
    AdminMapper adminMapper;

    @Override
    public JoinGroupVo joinGroup(JoinGroupRequest joinGroupRequest) {
        LambdaQueryWrapper<GroupAdmin> query = Wrappers.lambdaQuery(GroupAdmin.class);
        if(joinGroupRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(joinGroupRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), joinGroupRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        GroupInfo group = groupInfoMapper.selectById(joinGroupRequest.getGroupId());
        if(Objects.isNull(group)||(group.getStatus()!=20&&group.getStatus()!=40)){
            throw new BizException("小组不存在或未开放加入");
        }
        if(Objects.equals(group.getCreateAdmin(), joinGroupRequest.getId())){
            throw new BizException("创建者无法加入小组");
        }
        GroupAdmin groupAdmin0 = new GroupAdmin();
        groupAdmin0.setAdminId(joinGroupRequest.getId());
        groupAdmin0.setGroupId(joinGroupRequest.getGroupId());
        QueryWrapper<GroupAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(groupAdmin0);
        GroupAdmin groupAdmin = groupAdminMapper.selectOne(
                query.eq(GroupAdmin::getGroupId,joinGroupRequest.getGroupId())
                .eq(GroupAdmin::getAdminId,joinGroupRequest.getId())
        );
        if(!Objects.isNull(groupAdmin)){
            if(groupAdmin.getIsDelete()==0){
                throw new BizException("管理员已加入小组");
            }
            else{
                groupAdmin.setIsDelete(0);
                groupAdmin0.setCode("group-admin-"+ UUID.randomUUID());
                groupAdmin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                groupAdminMapper.updateById(groupAdmin);
            }
        }
        else{
            groupAdmin0.setCode("group-admin-"+ UUID.randomUUID());
            groupAdminMapper.insert(groupAdmin0);
        }
        JoinGroupVo joinGroupVo = new JoinGroupVo();
        BeanUtils.copyProperties(group,joinGroupVo);
        joinGroupVo.setTopic("成功加入小组");
        return joinGroupVo;
    }

    @Override
    public JoinGroupVo quitGroup(JoinGroupRequest joinGroupRequest) {
        LambdaQueryWrapper<GroupAdmin> query = Wrappers.lambdaQuery(GroupAdmin.class);
        if(joinGroupRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(joinGroupRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), joinGroupRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        GroupInfo group = groupInfoMapper.selectById(joinGroupRequest.getGroupId());
        if(Objects.isNull(group)||(group.getStatus()!=20&&group.getStatus()!=40)){
            throw new BizException("小组不存在或未开放退出");
        }
        if(Objects.equals(group.getCreateAdmin(), joinGroupRequest.getId())){
            throw new BizException("创建者无法退出小组");
        }
        GroupAdmin groupAdmin0 = new GroupAdmin();
        GroupAdmin groupAdmin = groupAdminMapper.selectOne(
                query.eq(GroupAdmin::getAdminId,joinGroupRequest.getId())
                .eq(GroupAdmin::getGroupId,joinGroupRequest.getId())
        );
        if(Objects.isNull(groupAdmin)){
            throw new BizException("该管理员不在该小组内");
        }
        if(groupAdmin.getIsDelete()==1){
            throw new BizException("该管理员不在该小组内");
        }
        groupAdmin.setIsDelete(1);
        groupAdmin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        groupAdminMapper.updateById(groupAdmin);
        JoinGroupVo joinGroupVo = new JoinGroupVo();
        BeanUtils.copyProperties(group,joinGroupVo);
        joinGroupVo.setTopic("成功退出小组");
        return joinGroupVo;
    }
}
