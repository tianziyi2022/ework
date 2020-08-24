package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.request.ChangeGroupRequest;
import com.hebutgo.ework.entity.request.CreateGroupRequest;
import com.hebutgo.ework.entity.vo.CreateGroupVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.mapper.GroupInfoMapper;
import com.hebutgo.ework.service.IGroupInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        if(!"".equals(createGroupRequest.getGroupCode())){
            GroupInfo group0 = new GroupInfo();
            group0.setGroupCode(createGroupRequest.getGroupCode());
            QueryWrapper<GroupInfo> queryWrapper0 = new QueryWrapper<>();
            queryWrapper0.setEntity(group0);
            if(!Objects.isNull(groupInfoMapper.selectOne(queryWrapper0))){
                throw new BizException("邀请码重复");
            }
        }
        GroupInfo group = new GroupInfo();
        group.setCreateAdmin(admin.getId());
        group.setDescriptions(createGroupRequest.getDescription());
        String groupId = "group-"+ UUID.randomUUID();
        if("".equals(createGroupRequest.getGroupCode())){
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
        createGroupVo.setId(group1.getId());
        createGroupVo.setGroupCode(group1.getGroupCode());
        createGroupVo.setGroupId(group1.getGroupId());
        createGroupVo.setGroupName(group1.getGroupName());
        return createGroupVo;
    }

    @Override
    public CreateGroupVo change(ChangeGroupRequest changeGroupRequest) {
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
        if(!"".equals(changeGroupRequest.getGroupCode())){
            GroupInfo group0 = new GroupInfo();
            group0.setGroupCode(changeGroupRequest.getGroupCode());
            QueryWrapper<GroupInfo> queryWrapper0 = new QueryWrapper<>();
            queryWrapper0.setEntity(group0);
            if(!Objects.isNull(groupInfoMapper.selectOne(queryWrapper0))){
                throw new BizException("邀请码重复");
            }
        }
        if(!"".equals(changeGroupRequest.getDescription())){
            group.setDescriptions(changeGroupRequest.getDescription());
        }
        if(!"".equals(changeGroupRequest.getGroupCode())){
            group.setGroupCode(changeGroupRequest.getGroupCode());
        }
        if(!"".equals(changeGroupRequest.getGroupName())){
            group.setGroupName(changeGroupRequest.getGroupName());
        }
        if(changeGroupRequest.getStatus()!=0){
            group.setStatus(changeGroupRequest.getStatus());
        }
        group.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        groupInfoMapper.updateById(group);
        GroupInfo group1 = groupInfoMapper.selectById(group.getId());
        CreateGroupVo createGroupVo = new CreateGroupVo();
        createGroupVo.setId(group1.getId());
        createGroupVo.setGroupCode(group1.getGroupCode());
        createGroupVo.setGroupId(group1.getGroupId());
        createGroupVo.setGroupName(group1.getGroupName());
        return createGroupVo;
    }
}
