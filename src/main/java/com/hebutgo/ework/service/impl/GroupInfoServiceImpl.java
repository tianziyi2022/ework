package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.request.CreateGroupRequest;
import com.hebutgo.ework.entity.vo.CreateGroupVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.mapper.GroupInfoMapper;
import com.hebutgo.ework.service.IGroupInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        if(!admin.getToken().equals(createGroupRequest.getToken())){
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
        group.setStatus(30);
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
}
