package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.GroupAdmin;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.request.ChangeGroupRequest;
import com.hebutgo.ework.entity.request.CreateGroupRequest;
import com.hebutgo.ework.entity.request.GroupDetailRequest;
import com.hebutgo.ework.entity.vo.CreateGroupVo;
import com.hebutgo.ework.entity.vo.GroupDetailVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.mapper.GroupInfoMapper;
import com.hebutgo.ework.service.IGroupInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 小组信息表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-24
 */
@Service
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements IGroupInfoService {

    @Resource
    AdminMapper adminMapper;

    @Resource
    GroupInfoMapper groupInfoMapper;

    @Override
    public CreateGroupVo create(CreateGroupRequest createGroupRequest) {
        LambdaQueryWrapper<GroupInfo> query = Wrappers.lambdaQuery(GroupInfo.class);
        if(createGroupRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(createGroupRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), createGroupRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        if(!Objects.isNull(createGroupRequest.getGroupCode())){
            if(!Objects.isNull(groupInfoMapper.selectOne(query.eq(GroupInfo::getGroupCode,createGroupRequest.getGroupCode())))){
                throw new BizException("邀请码重复");
            }
        }
        GroupInfo group = new GroupInfo();
        group.setCreateAdmin(admin.getId());
        group.setDescriptions(createGroupRequest.getDescription());
        String groupId = "group-"+ UUID.randomUUID();
        if(Objects.isNull(createGroupRequest.getGroupCode())){
            group.setGroupCode(groupId);
        }else{
            group.setGroupCode(createGroupRequest.getGroupCode());
        }
        group.setGroupId(groupId);
        group.setGroupName(createGroupRequest.getGroupName());
        group.setStatus(40);
        groupInfoMapper.insert(group);
        QueryWrapper<GroupInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(group);
        GroupInfo group1 = groupInfoMapper.selectOne(queryWrapper);
        CreateGroupVo createGroupVo = new CreateGroupVo();
        BeanUtils.copyProperties(group1,createGroupVo);
        return createGroupVo;
    }

    @Override
    public CreateGroupVo change(ChangeGroupRequest changeGroupRequest) {
        LambdaQueryWrapper<GroupInfo> query = Wrappers.lambdaQuery(GroupInfo.class);
        if(changeGroupRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(changeGroupRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), changeGroupRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        GroupInfo group = groupInfoMapper.selectById(changeGroupRequest.getGroupId());
        if(Objects.isNull(group)||group.getStatus()==100){
            throw new BizException("小组不存在");
        }
        if(!Objects.equals(group.getCreateAdmin(), changeGroupRequest.getId())){
            throw new BizException("该用户不是创建者，无权修改小组信息");
        }
        BeanUtils.copyProperties(changeGroupRequest,group);
        if(!Objects.isNull(changeGroupRequest.getGroupCode())){
            if(!Objects.isNull(groupInfoMapper.selectOne(query.eq(GroupInfo::getGroupCode,changeGroupRequest.getGroupCode())))){
                throw new BizException("邀请码重复");
            }
        }

        group.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        groupInfoMapper.updateById(group);
        GroupInfo group1 = groupInfoMapper.selectById(group.getId());
        CreateGroupVo createGroupVo = new CreateGroupVo();
        BeanUtils.copyProperties(group1,createGroupVo);
        return createGroupVo;
    }

    @Override
    public GroupDetailVo detail(GroupDetailRequest groupDetailRequest) {
        GroupDetailVo groupDetailVo = new GroupDetailVo();
        GroupInfo group;
        if(Objects.isNull(groupDetailRequest.getId())){
            group = groupInfoMapper.selectById(groupDetailRequest.getId());
            if(Objects.isNull(group)){
                throw new BizException("不存在符合条件的小组");
            }
            if(!Objects.isNull(groupDetailRequest.getGroupCode())){
                if(!Objects.equals(groupDetailRequest.getGroupCode(), group.getGroupCode())){
                    throw new BizException("不存在符合条件的小组");
                }
            }
            if(!Objects.isNull(groupDetailRequest.getGroupId())){
                if(!Objects.equals(groupDetailRequest.getGroupId(), group.getGroupId())){
                    throw new BizException("不存在符合条件的小组");
                }
            }
        } else {
            QueryWrapper<GroupInfo> groupInfoQueryWrapper = new QueryWrapper<>();
            GroupInfo groupInfo = new GroupInfo();
            boolean flag = true;
            if(!Objects.isNull(groupDetailRequest.getGroupCode())){
                groupInfo.setGroupCode(groupDetailRequest.getGroupCode());
                flag = false;
            }
            if(!Objects.isNull(groupDetailRequest.getGroupId())){
                groupInfo.setGroupId(groupDetailRequest.getGroupId());
                flag = false;
            }
            if(flag){
                throw new BizException("未输入查询条件");
            }
            groupInfoQueryWrapper.setEntity(groupInfo);
            group = groupInfoMapper.selectOne(groupInfoQueryWrapper);
        }
        groupDetailVo.setCreateAdminName(adminMapper.selectById(group.getCreateAdmin()).getUserName());
        return groupDetailVo;
    }
}
