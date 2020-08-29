package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.*;
import com.hebutgo.ework.entity.request.CompleteWorkRequest;
import com.hebutgo.ework.entity.request.CorrectWorkRequest;
import com.hebutgo.ework.entity.request.ReturnWorkRequest;
import com.hebutgo.ework.entity.request.SubmitWorkRequest;
import com.hebutgo.ework.entity.vo.SubmitWorkVo;
import com.hebutgo.ework.mapper.*;
import com.hebutgo.ework.service.IWorkSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * <p>
 * 提交作业内容表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class WorkSubmitServiceImpl extends ServiceImpl<WorkSubmitMapper, WorkSubmit> implements IWorkSubmitService {

    @Resource
    UserMapper userMapper;

    @Resource
    AdminMapper adminMapper;

    @Resource
    GroupInfoMapper groupInfoMapper;

    @Resource
    GroupAdminMapper groupAdminMapper;

    @Resource
    WorkSubmitMapper workSubmitMapper;

    @Resource
    WorkDemandMapper workDemandMapper;

    @Override
    public SubmitWorkVo complete(CompleteWorkRequest completeWorkRequest) {
        if(completeWorkRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(completeWorkRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), completeWorkRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkSubmit workSubmit = workSubmitMapper.selectById(completeWorkRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        if(!Objects.equals(workSubmit.getStudentId(), completeWorkRequest.getId())){
            throw new BizException("作业完成人不匹配");
        }
        switch(workSubmit.getStatus()){
            case 110:
            case 120:
                break;
            case 100:
                workSubmit.setStatus(110);
                break;
            case 210:
            case 220:
            case 230:
                throw new BizException("作业已提交，无法修改，请先撤回");
            default:
                throw new BizException("作业状态不允许保存");
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        if(workDemand.getStatus()==0){
            throw new BizException("作业无效");
        }
        if(!Objects.isNull(workDemand.getEndTime())){
            if(workDemand.getEndTime().before(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业已结束提交");
            }
        }
        if(!"".equals(completeWorkRequest.getText())){
            workSubmit.setText(completeWorkRequest.getText());
        }
        if(completeWorkRequest.getAppendixUrl1()!=0){
            workSubmit.setAppendixUrl1(completeWorkRequest.getAppendixUrl1());
        }
        if(completeWorkRequest.getAppendixUrl2()!=0){
            workSubmit.setAppendixUrl2(completeWorkRequest.getAppendixUrl2());
        }
        if(completeWorkRequest.getAppendixUrl3()!=0){
            workSubmit.setAppendixUrl3(completeWorkRequest.getAppendixUrl3());
        }
        if(completeWorkRequest.getAppendixUrl4()!=0){
            workSubmit.setAppendixUrl4(completeWorkRequest.getAppendixUrl4());
        }
        if(completeWorkRequest.getAppendixUrl5()!=0){
            workSubmit.setAppendixUrl5(completeWorkRequest.getAppendixUrl5());
        }
        workSubmit.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        try {
            workSubmitMapper.updateById(workSubmit);
        }catch(Exception e){
            throw new BizException("保存时错误，请检查附件");
        }
        SubmitWorkVo submitWorkVo = new SubmitWorkVo();
        submitWorkVo.setId(workSubmit.getId());
        submitWorkVo.setTitle(workDemand.getTitle());
        submitWorkVo.setUserName(user.getUserName());
        return submitWorkVo;
    }

    @Override
    public SubmitWorkVo submit(SubmitWorkRequest submitWorkRequest) {
        if(submitWorkRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(submitWorkRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), submitWorkRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkSubmit workSubmit = workSubmitMapper.selectById(submitWorkRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        if(!Objects.equals(workSubmit.getStudentId(), submitWorkRequest.getId())){
            throw new BizException("作业完成人不匹配");
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        if(!Objects.isNull(workDemand.getStartTime())){
            if(workDemand.getStartTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未开始提交");
            }
        }
        if(!Objects.isNull(workDemand.getEndTime())){
            if(workDemand.getEndTime().before(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业已结束提交");
            }
        }
        switch(workSubmit.getStatus()){
            case 110:
                workSubmit.setStatus(210);
                break;
            case 120:
                workSubmit.setStatus(220);
                break;
            case 100:
                throw new BizException("作业未完成或未保存");
            case 210:
            case 220:
            case 230:
                throw new BizException("作业已提交，无法重复提交");
            default:
                throw new BizException("作业状态不允许提交");
        }
        workSubmit.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workSubmitMapper.updateById(workSubmit);
        SubmitWorkVo submitWorkVo = new SubmitWorkVo();
        submitWorkVo.setId(workSubmit.getId());
        submitWorkVo.setTitle(workDemand.getTitle());
        submitWorkVo.setUserName(user.getUserName());
        return submitWorkVo;
    }

    @Override
    public SubmitWorkVo withdraw(SubmitWorkRequest submitWorkRequest) {
        if(submitWorkRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(submitWorkRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), submitWorkRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkSubmit workSubmit = workSubmitMapper.selectById(submitWorkRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        if(!Objects.equals(workSubmit.getStudentId(), submitWorkRequest.getId())){
            throw new BizException("作业完成人不匹配");
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        if(!Objects.isNull(workDemand.getStartTime())){
            if(workDemand.getStartTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未开始提交，不能撤回");
            }
        }
        if(!Objects.isNull(workDemand.getEndTime())){
            if(workDemand.getEndTime().before(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业已结束提交，不能撤回");
            }
        }
        switch(workSubmit.getStatus()){
            case 210:
            case 220:
                workSubmit.setStatus(120);
                break;
            case 100:
            case 110:
            case 120:
                throw new BizException("作业未提交，无法撤回");
            case 230:
                throw new BizException("作业已批改，无法撤回");
            default:
                throw new BizException("作业状态不允许撤回");
        }
        workSubmit.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workSubmitMapper.updateById(workSubmit);
        SubmitWorkVo submitWorkVo = new SubmitWorkVo();
        submitWorkVo.setId(workSubmit.getId());
        submitWorkVo.setTitle(workDemand.getTitle());
        submitWorkVo.setUserName(user.getUserName());
        return submitWorkVo;
    }

    @Override
    public SubmitWorkVo returnWork(ReturnWorkRequest returnWorkRequest) {
        if(returnWorkRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(returnWorkRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), returnWorkRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkSubmit workSubmit = workSubmitMapper.selectById(returnWorkRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        User user = userMapper.selectById(workSubmit.getId());
        GroupInfo group = groupInfoMapper.selectById(user.getGroupId());
        if(!Objects.equals(group.getCreateAdmin(), returnWorkRequest.getId())){
            GroupAdmin groupAdmin = new GroupAdmin();
            QueryWrapper<GroupAdmin> groupAdminQueryWrapper = new QueryWrapper<>();
            groupAdmin.setIsDelete(0);
            groupAdmin.setAdminId(returnWorkRequest.getId());
            groupAdmin.setGroupId(user.getGroupId());
            groupAdminQueryWrapper.setEntity(groupAdmin);
            if(Objects.isNull(groupAdminMapper.selectOne(groupAdminQueryWrapper))){
                throw new BizException("该管理员不在作业完成人小组内，无法撤回");
            }
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        if(!Objects.isNull(workDemand.getStartTime())){
            if(workDemand.getStartTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未开始提交，不能撤回");
            }
        }
        if(!Objects.isNull(workDemand.getEndTime())){
            if(workDemand.getEndTime().before(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业已结束提交，不能撤回");
            }
        }
        switch(workSubmit.getStatus()){
            case 210:
            case 220:
            case 230:
                workSubmit.setStatus(120);
                break;
            case 100:
            case 110:
            case 120:
                throw new BizException("作业未提交，无法撤回");
            default:
                throw new BizException("作业状态不允许撤回");
        }
        workSubmit.setCorrectId(null);
        workSubmit.setScore(null);
        workSubmit.setComment(null);
        workSubmit.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workSubmitMapper.updateById(workSubmit);
        SubmitWorkVo submitWorkVo = new SubmitWorkVo();
        submitWorkVo.setId(workSubmit.getId());
        submitWorkVo.setTitle(workDemand.getTitle());
        submitWorkVo.setUserName(user.getUserName());
        return submitWorkVo;
    }

    @Override
    public SubmitWorkVo correct(CorrectWorkRequest correctWorkRequest) {
        if(correctWorkRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(correctWorkRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), correctWorkRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkSubmit workSubmit = workSubmitMapper.selectById(correctWorkRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        User user = userMapper.selectById(workSubmit.getStudentId());
        GroupInfo group = groupInfoMapper.selectById(user.getGroupId());
        if(!Objects.equals(group.getCreateAdmin(), correctWorkRequest.getId())){
            GroupAdmin groupAdmin = new GroupAdmin();
            QueryWrapper<GroupAdmin> groupAdminQueryWrapper = new QueryWrapper<>();
            groupAdmin.setIsDelete(0);
            groupAdmin.setAdminId(correctWorkRequest.getId());
            groupAdmin.setGroupId(user.getGroupId());
            groupAdminQueryWrapper.setEntity(groupAdmin);
            if(Objects.isNull(groupAdminMapper.selectOne(groupAdminQueryWrapper))){
                throw new BizException("该管理员不在作业完成人小组内，无法撤回");
            }
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        if(!Objects.isNull(workDemand.getStartTime())){
            if(workDemand.getStartTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未开始提交，不能批改");
            }
        }
        if(!Objects.isNull(workDemand.getEndTime())){
            if(workDemand.getEndTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未结束提交，不能批改");
            }
        }
        switch(workSubmit.getStatus()){
            case 210:
            case 220:
                workSubmit.setStatus(230);
                break;
            case 100:
            case 110:
            case 120:
                throw new BizException("作业未提交，无法批改");
            case 230:
                throw new BizException("作业已批改，不能重复批改，请退回或重判");
            default:
                throw new BizException("作业状态不允许批改");
        }
        workSubmit.setCorrectId(correctWorkRequest.getId());
        workSubmit.setComment(correctWorkRequest.getComment());
        workSubmit.setScore(correctWorkRequest.getScore());
        workSubmit.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workSubmitMapper.updateById(workSubmit);
        SubmitWorkVo submitWorkVo = new SubmitWorkVo();
        submitWorkVo.setId(workSubmit.getId());
        submitWorkVo.setTitle(workDemand.getTitle());
        submitWorkVo.setUserName(user.getUserName());
        return submitWorkVo;
    }

    @Override
    public SubmitWorkVo recorrect(CorrectWorkRequest correctWorkRequest) {
        if(correctWorkRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(correctWorkRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), correctWorkRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        WorkSubmit workSubmit = workSubmitMapper.selectById(correctWorkRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        User user = userMapper.selectById(workSubmit.getId());
        GroupInfo group = groupInfoMapper.selectById(user.getGroupId());
        if(!Objects.equals(group.getCreateAdmin(), correctWorkRequest.getId())){
            GroupAdmin groupAdmin = new GroupAdmin();
            QueryWrapper<GroupAdmin> groupAdminQueryWrapper = new QueryWrapper<>();
            groupAdmin.setIsDelete(0);
            groupAdmin.setAdminId(correctWorkRequest.getId());
            groupAdmin.setGroupId(user.getGroupId());
            groupAdminQueryWrapper.setEntity(groupAdmin);
            if(Objects.isNull(groupAdminMapper.selectOne(groupAdminQueryWrapper))){
                throw new BizException("该管理员不在作业完成人小组内，无法撤回");
            }
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        if(!Objects.isNull(workDemand.getStartTime())){
            if(workDemand.getStartTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未开始提交，不能批改");
            }
        }
        if(!Objects.isNull(workDemand.getEndTime())){
            if(workDemand.getEndTime().after(new Timestamp(System.currentTimeMillis()))){
                throw new BizException("作业未结束提交，不能批改");
            }
        }
        if(workSubmit.getStatus()!=230){
            throw new BizException("作业未批改，不能重判");
        }
        workSubmit.setCorrectId(correctWorkRequest.getId());
        workSubmit.setComment(correctWorkRequest.getComment());
        workSubmit.setScore(correctWorkRequest.getScore());
        workSubmit.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        workSubmitMapper.updateById(workSubmit);
        SubmitWorkVo submitWorkVo = new SubmitWorkVo();
        submitWorkVo.setId(workSubmit.getId());
        submitWorkVo.setTitle(workDemand.getTitle());
        submitWorkVo.setUserName(user.getUserName());
        return submitWorkVo;
    }
}
