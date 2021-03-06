package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.common.utils.FileDemandUtil;
import com.hebutgo.ework.entity.*;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.CreateDemandVo;
import com.hebutgo.ework.entity.vo.DemandDetailVo;
import com.hebutgo.ework.mapper.*;
import com.hebutgo.ework.service.IWorkDemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 * 作业需求（发布作业内容）表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class WorkDemandServiceImpl extends ServiceImpl<WorkDemandMapper, WorkDemand> implements IWorkDemandService {

    @Resource
    AdminMapper adminMapper;

    @Resource
    WorkDemandMapper workDemandMapper;

    @Resource
    FileDemandMapper fileDemandMapper;

    @Resource
    GroupInfoMapper groupInfoMapper;

    @Resource
    GroupAdminMapper groupAdminMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    WorkSubmitMapper workSubmitMapper;

    @Override
    public CreateDemandVo create(CreateDemandRequest createDemandRequest) {
        LambdaQueryWrapper<WorkDemand> query = Wrappers.lambdaQuery(WorkDemand.class);
        if(createDemandRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(createDemandRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), createDemandRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkDemand workDemand = new WorkDemand();
        BeanUtils.copyProperties(createDemandRequest,workDemand);
        if(Objects.isNull(createDemandRequest.getAppendixUrl())){
            FileDemand fileDemand = fileDemandMapper.selectById(createDemandRequest.getAppendixUrl());
            if(Objects.isNull(fileDemand)){
                throw new BizException("附件选择失败");
            }
            if(!Objects.isNull(fileDemand.getAdminId())&&!fileDemand.getAdminId().equals(createDemandRequest.getId())){
                throw new BizException("不能添加他人上传的附件");
            }
            workDemand.setAppendixUrl(createDemandRequest.getAppendixUrl());
        }
        workDemand.setStatus(10);
        workDemand.setDemandId("demand-"+ UUID.randomUUID());
        workDemandMapper.insert(workDemand);
        QueryWrapper<WorkDemand> workDemandQueryWrapper = new QueryWrapper<>();
        workDemandQueryWrapper.setEntity(workDemand);
        WorkDemand workDemand1 = workDemandMapper.selectOne(
                query.eq(WorkDemand::getDemandId,workDemand)
        );
        CreateDemandVo createDemandVo = new CreateDemandVo();
        BeanUtils.copyProperties(workDemand1,createDemandVo);
        return createDemandVo;
    }


    @Override
    public CreateDemandVo change(ChangeDemandRequest changeDemandRequest) {
        if(changeDemandRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(changeDemandRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), changeDemandRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkDemand workDemand = workDemandMapper.selectById(changeDemandRequest.getDemandId());
        if(Objects.isNull(workDemand)||workDemand.getStatus()==0){
            throw new BizException("作业已删除");
        }
        if(!Objects.equals(workDemand.getAnnouncerId(), changeDemandRequest.getId())){
            throw new BizException("非创建者不能修改作业要求");
        }
        if(Objects.isNull(changeDemandRequest.getAppendixUrl())){
            FileDemand fileDemand = fileDemandMapper.selectById(changeDemandRequest.getAppendixUrl());
            if(Objects.isNull(fileDemand)){
                throw new BizException("附件选择失败");
            }
            if(!Objects.isNull(fileDemand.getAdminId())&&!fileDemand.getAdminId().equals(changeDemandRequest.getId())){
                throw new BizException("不能添加他人上传的附件");
            }
            workDemand.setAppendixUrl(changeDemandRequest.getAppendixUrl());
        }
        if(!Objects.isNull(changeDemandRequest.getTitle())){
            workDemand.setTitle(changeDemandRequest.getTitle());
        }
        if(!Objects.isNull(changeDemandRequest.getDescription())){
            workDemand.setDescription(changeDemandRequest.getDescription());
        }
        switch(workDemand.getStatus()){
            case 110:
                workDemand.setStatus(120);
                break;
            case 10:
                workDemand.setStatus(20);
                break;
            default:
        }
        workDemand.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workDemandMapper.updateById(workDemand);
        WorkDemand workDemand1 = workDemandMapper.selectById(workDemand.getId());
        CreateDemandVo createDemandVo = new CreateDemandVo();
        BeanUtils.copyProperties(workDemand1,createDemandVo);
        return createDemandVo;
    }

    @Override
    public CreateDemandVo announce(AnnounceDemandRequest announceDemandRequest) {
        if(!Objects.equals(announceDemandRequest.getStartTimeMills(),0)){
            announceDemandRequest.setStartTime(new Timestamp(announceDemandRequest.getStartTimeMills().longValue()+28800000));
        }
        if(!Objects.equals(announceDemandRequest.getEndTimeMills(),0)){
            announceDemandRequest.setEndTime(new Timestamp(announceDemandRequest.getEndTimeMills().longValue()+28800000));
        }
        if(announceDemandRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(announceDemandRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), announceDemandRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkDemand workDemand = workDemandMapper.selectById(announceDemandRequest.getDemandId());
        if(workDemand.getStatus()==0){
            throw new BizException("作业已删除");
        }
        if(!Objects.equals(workDemand.getAnnouncerId(), announceDemandRequest.getId())){
            throw new BizException("非作业创建者不能发布作业");
        }
        if(workDemand.getStatus()==110||workDemand.getStatus()==120){
            throw new BizException("作业不能重复发布");
        }
        GroupInfo group = groupInfoMapper.selectById(announceDemandRequest.getGroupId());
        if(Objects.isNull(group)||group.getStatus()==100){
            throw new BizException("小组不存在");
        }
        if(group.getStatus()==80){
            throw new BizException("小组已停用");
        }
        if(!Objects.equals(group.getCreateAdmin(), announceDemandRequest.getId())){
            GroupAdmin groupAdmin = new GroupAdmin();
            QueryWrapper<GroupAdmin> groupAdminQueryWrapper = new QueryWrapper<>();
            groupAdmin.setIsDelete(0);
            groupAdmin.setAdminId(announceDemandRequest.getId());
            groupAdmin.setGroupId(announceDemandRequest.getGroupId());
            groupAdminQueryWrapper.setEntity(groupAdmin);
            if(Objects.isNull(groupAdminMapper.selectOne(
                    Wrappers.lambdaQuery(GroupAdmin.class)
                    .eq(GroupAdmin::getAdminId,announceDemandRequest.getId())
                    .eq(GroupAdmin::getGroupId,announceDemandRequest.getGroupId())
                    .eq(GroupAdmin::getIsDelete,0)
            ))){
                throw new BizException("该管理员不是该小组管理员或创建者");
            }
        }
        User user0 = new User();
        user0.setGroupId(announceDemandRequest.getGroupId());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.setEntity(user0);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        for(User user1:userList){
            WorkSubmit workSubmit = new WorkSubmit();
            workSubmit.setSubmitId(workDemand.getDemandId()+"-submit-"+UUID.randomUUID());
            workSubmit.setAnnouncerId(announceDemandRequest.getId());
            workSubmit.setDemandId(announceDemandRequest.getDemandId());
            workSubmit.setStatus(100);
            workSubmit.setStudentId(user1.getId());
            workSubmitMapper.insert(workSubmit);
        }
        if(!Objects.isNull(announceDemandRequest.getStartTime())){
            workDemand.setStartTime(announceDemandRequest.getStartTime());
        }
        if(!Objects.isNull(announceDemandRequest.getEndTime())){
            workDemand.setEndTime(announceDemandRequest.getEndTime());
        }
        workDemand.setStatus(110);
        workDemand.setGroupId(group.getId());
        workDemand.setStudentCount(userList.size());
        workDemand.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workDemandMapper.updateById(workDemand);
        QueryWrapper<WorkDemand> workDemandQueryWrapper = new QueryWrapper<>();
        workDemandQueryWrapper.setEntity(workDemand);
        WorkDemand workDemand1 = workDemandMapper.selectById(workDemand.getId());
        CreateDemandVo createDemandVo = new CreateDemandVo();
        BeanUtils.copyProperties(workDemand1,createDemandVo);
        return createDemandVo;
    }

    @Override
    public CreateDemandVo delete(DeleteDemandRequest deleteDemandRequest) {
        if(deleteDemandRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(deleteDemandRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), deleteDemandRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkDemand workDemand = workDemandMapper.selectById(deleteDemandRequest.getDemandId());
        if(workDemand.getStatus()==0){
            throw new BizException("作业已删除");
        }
        if(!Objects.equals(workDemand.getAnnouncerId(), deleteDemandRequest.getId())){
            throw new BizException("非作业创建者不能删除作业");
        }
        if(workDemand.getStatus()==110||workDemand.getStatus()==120){
            throw new BizException("作业状态不允许删除("+workDemand.getStatus()+")");
        }
        workDemand.setStatus(0);
        workDemand.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        List<WorkSubmit> workSubmitList = workSubmitMapper.selectList(
                Wrappers.lambdaQuery(WorkSubmit.class)
                .eq(WorkSubmit::getDemandId,workDemand.getId())
        );
        for(WorkSubmit workSubmit1 : workSubmitList){
            workSubmit1.setStatus(0);
            workSubmit1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            workSubmitMapper.updateById(workSubmit1);
        }
        workDemandMapper.updateById(workDemand);
        WorkDemand workDemand1 = workDemandMapper.selectById(workDemand.getId());
        CreateDemandVo createDemandVo = new CreateDemandVo();
        BeanUtils.copyProperties(workDemand1,createDemandVo);
        return createDemandVo;
    }

    @Override
    public CreateDemandVo withdraw(DeleteDemandRequest deleteDemandRequest) {
        if(deleteDemandRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(deleteDemandRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), deleteDemandRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkDemand workDemand = workDemandMapper.selectById(deleteDemandRequest.getDemandId());
        if(workDemand.getStatus()==0){
            throw new BizException("作业已删除");
        }
        if(!Objects.equals(workDemand.getAnnouncerId(), deleteDemandRequest.getId())){
            throw new BizException("非作业创建者不能撤回作业");
        }
        if(workDemand.getStatus()==10||workDemand.getStatus()==20){
            throw new BizException("作业状态不允许撤回("+workDemand.getStatus()+")");
        }
        workDemand.setStatus(20);
        workDemand.setSubmitCount(0);
        workDemand.setStudentCount(0);
        workDemand.setStartTime(null);
        workDemand.setEndTime(null);
        workDemand.setGroupId(null);
        workDemand.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        List<WorkSubmit> workSubmitList = workSubmitMapper.selectList(
                Wrappers.lambdaQuery(WorkSubmit.class)
                .eq(WorkSubmit::getDemandId,workDemand.getId())
        );
        for(WorkSubmit workSubmit1 : workSubmitList){
            workSubmit1.setStatus(300);
            workSubmit1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            workSubmitMapper.updateById(workSubmit1);
        }
        workDemandMapper.updateById(workDemand);
        WorkDemand workDemand1 = workDemandMapper.selectById(workDemand.getId());
        CreateDemandVo createDemandVo = new CreateDemandVo();
        BeanUtils.copyProperties(workDemand1,createDemandVo);
        return createDemandVo;
    }

    @Override
    public DemandDetailVo detail(DemandDetailRequest demandDetailRequest) {
        if(demandDetailRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(demandDetailRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), demandDetailRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkDemand workDemand = workDemandMapper.selectById(demandDetailRequest.getDemandId());
        if(workDemand.getStatus()==0){
            throw new BizException("作业已删除");
        }
        if(!Objects.equals(workDemand.getAnnouncerId(), demandDetailRequest.getId())){
            throw new BizException("非作业创建者不能查看作业");
        }
        DemandDetailVo demandDetailVo = new DemandDetailVo(workDemand);
        if(!Objects.isNull(workDemand.getAppendixUrl())){
            FileDemand fileDemand = fileDemandMapper.selectById(workDemand.getAppendixUrl());
            if(Objects.isNull(fileDemand)||fileDemand.getIsDelete()==1){
                throw new BizException("文件不存在");
            }
            demandDetailVo.setAppendixUrl(CommonConstant.STORE_FOLDER+CommonConstant.DEMAND_FOLDER+fileDemand.getUrl());
        }
        return demandDetailVo;
    }

    @Override
    public List<DemandDetailVo> detailList(DemandDetailListRequest demandDetailListRequest) {
        if(demandDetailListRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(demandDetailListRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), demandDetailListRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        List<WorkDemand> workDemandList = workDemandMapper.selectList(
                Wrappers.lambdaQuery(WorkDemand.class)
                .eq(WorkDemand::getAnnouncerId,demandDetailListRequest.getId())
        );
        List<DemandDetailVo> demandDetailVoList = new ArrayList<>();
        for(WorkDemand workDemand1 : workDemandList){
            DemandDetailVo demandDetailVo = new DemandDetailVo(workDemand1);
            if(!Objects.isNull(workDemand1.getAppendixUrl())){
                FileDemand fileDemand = fileDemandMapper.selectById(workDemand1.getAppendixUrl());
                if(Objects.isNull(fileDemand)||fileDemand.getIsDelete()==1){
                    throw new BizException("文件不存在");
                }
                demandDetailVo.setAppendixUrl(CommonConstant.STORE_FOLDER+CommonConstant.DEMAND_FOLDER+fileDemand.getUrl());
            }
            demandDetailVoList.add(demandDetailVo);
        }
        return demandDetailVoList;
    }
}
