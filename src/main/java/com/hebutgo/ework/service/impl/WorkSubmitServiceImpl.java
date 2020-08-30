package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.*;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.SubmitWorkVo;
import com.hebutgo.ework.entity.vo.WorkDetailVo;
import com.hebutgo.ework.mapper.*;
import com.hebutgo.ework.service.IWorkSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
        workDemand.setSubmitCount(workDemand.getSubmitCount()+1);
        workDemandMapper.updateById(workDemand);
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
        workDemand.setSubmitCount(workDemand.getSubmitCount()-1);
        workDemandMapper.updateById(workDemand);
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
        workDemand.setSubmitCount(workDemand.getSubmitCount()-1);
        workDemandMapper.updateById(workDemand);
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

    @Override
    public WorkDetailVo detail(WorkDetailRequest workDetailRequest) {
        WorkSubmit workSubmit = workSubmitMapper.selectById(workDetailRequest.getSubmitId());
        if(Objects.isNull(workSubmit)||workSubmit.getStatus()==0){
            throw new BizException("作业信息不存在");
        }
        if(workDetailRequest.getType()==10){
            Admin admin = adminMapper.selectById(workDetailRequest.getId());
            if(Objects.isNull(admin)){
                throw new BizException("用户不存在");
            }
            if(admin.getStatus()!=10) {
                throw new BizException("用户状态异常");
            }
            if(!Objects.equals(admin.getToken(), workDetailRequest.getToken())) {
                throw new BizException("未登陆或登陆超时");
            }
            if(workSubmit.getStatus()==100||workSubmit.getStatus()==110||workSubmit.getStatus()==120){
                throw new BizException("作业未提交，不能查看");
            }
            if(workSubmit.getStatus()!=210&&workSubmit.getStatus()!=220&&workSubmit.getStatus()!=230){
                throw new BizException("作业状态不允许查看");
            }
            User user = userMapper.selectById(workSubmit.getId());
            GroupInfo group = groupInfoMapper.selectById(user.getGroupId());
            if(!Objects.equals(group.getCreateAdmin(), workDetailRequest.getId())){
                GroupAdmin groupAdmin = new GroupAdmin();
                QueryWrapper<GroupAdmin> groupAdminQueryWrapper = new QueryWrapper<>();
                groupAdmin.setIsDelete(0);
                groupAdmin.setAdminId(workDetailRequest.getId());
                groupAdmin.setGroupId(user.getGroupId());
                groupAdminQueryWrapper.setEntity(groupAdmin);
                if(Objects.isNull(groupAdminMapper.selectOne(groupAdminQueryWrapper))){
                    throw new BizException("该管理员不在作业完成人小组内，无法查看");
                }
            }
        }else if(workDetailRequest.getType()==20){
            User user = userMapper.selectById(workDetailRequest.getId());
            if(Objects.isNull(user)){
                throw new BizException("用户不存在");
            }
            if(user.getStatus()!=10) {
                throw new BizException("用户状态异常");
            }
            if(!Objects.equals(user.getToken(), workDetailRequest.getToken())){
                throw new BizException("未登陆或登陆超时");
            }
            if(!Objects.equals(workSubmit.getStudentId(), workDetailRequest.getId())){
                throw new BizException("作业完成人不匹配");
            }
            if(workSubmit.getStatus()==300){
                throw new BizException("作业已被管理员撤回，不能查看");
            }
        }else{
            throw new BizException("用户类型错误");
        }
        WorkDemand workDemand = workDemandMapper.selectById(workSubmit.getDemandId());
        User user = userMapper.selectById(workSubmit.getStudentId());
        Admin announcer = adminMapper.selectById(workDemand.getAnnouncerId());
        if(Objects.isNull(announcer)||Objects.isNull(user)){
            throw new BizException("查询错误");
        }
        WorkDetailVo workDetailVo;
        if(Objects.isNull(workSubmit.getCorrectId())){
            workDetailVo = new WorkDetailVo(workSubmit,workDemand,user,announcer.getUserName());
        }else{
            Admin correct = adminMapper.selectById(workSubmit.getCorrectId());
            workDetailVo = new WorkDetailVo(workSubmit,workDemand,user,announcer.getUserName(),correct.getUserName());
        }
        return workDetailVo;
    }

    @Override
    public List<WorkDetailVo> detailList(WorkDetailListRequest workDetailListRequest) {
        List<WorkDetailVo> workDetailVoList = new ArrayList<>();
        if(workDetailListRequest.getType()==10) {
            Admin admin = adminMapper.selectById(workDetailListRequest.getId());
            if (Objects.isNull(admin)) {
                throw new BizException("用户不存在");
            }
            if (admin.getStatus() != 10) {
                throw new BizException("用户状态异常");
            }
            if (!Objects.equals(admin.getToken(), workDetailListRequest.getToken())) {
                throw new BizException("未登陆或登陆超时");
            }
            List<Integer> groupIdList = new ArrayList<>();
            QueryWrapper<GroupInfo> groupInfoQueryWrapper = new QueryWrapper<>();
            GroupInfo groupInfo0 = new GroupInfo();
            groupInfo0.setCreateAdmin(workDetailListRequest.getId());
            groupInfoQueryWrapper.setEntity(groupInfo0);
            List<GroupInfo> groupInfoList = groupInfoMapper.selectList(groupInfoQueryWrapper);
            for(GroupInfo groupInfo1 : groupInfoList){
                groupIdList.add(groupInfo1.getId());
            }
            QueryWrapper<GroupAdmin> groupAdminQueryWrapper = new QueryWrapper<>();
            GroupAdmin groupAdmin0 = new GroupAdmin();
            groupAdmin0.setIsDelete(0);
            groupAdmin0.setAdminId(workDetailListRequest.getId());
            groupAdminQueryWrapper.setEntity(groupAdmin0);
            List<GroupAdmin> groupAdminList = groupAdminMapper.selectList(groupAdminQueryWrapper);
            for(GroupAdmin groupAdmin1 : groupAdminList){
                groupIdList.add(groupAdmin1.getGroupId());
            }
            for (Integer integer : groupIdList) {
                QueryWrapper<WorkDemand> workDemandQueryWrapper = new QueryWrapper<>();
                WorkDemand workDemand0 = new WorkDemand();
                workDemand0.setGroupId(integer);
                workDemandQueryWrapper.setEntity(workDemand0);
                List<WorkDemand> workDemandList1 = workDemandMapper.selectList(workDemandQueryWrapper);
                for (WorkDemand workDemand1 : workDemandList1) {
                    if (workDemand1.getStatus() != 300 && workDemand1.getStatus() != 0) {
                        QueryWrapper<WorkSubmit> workSubmitQueryWrapper = new QueryWrapper<>();
                        WorkSubmit workSubmit0 = new WorkSubmit();
                        workSubmit0.setDemandId(workDemand1.getId());
                        workSubmitQueryWrapper.setEntity(workSubmit0);
                        List<WorkSubmit> workSubmitList = workSubmitMapper.selectList(workSubmitQueryWrapper);
                        for (WorkSubmit workSubmit1 : workSubmitList) {
                            if (workSubmit1.getStatus() == 210 || workSubmit1.getStatus() == 220 || workSubmit1.getStatus() == 230) {
                                User user = userMapper.selectById(workSubmit1.getStudentId());
                                Admin announcer = adminMapper.selectById(workDemand1.getAnnouncerId());
                                if (Objects.isNull(announcer) || Objects.isNull(user)) {
                                    throw new BizException("查询错误");
                                }
                                WorkDetailVo workDetailVo;
                                if (Objects.isNull(workSubmit1.getCorrectId())) {
                                    workDetailVo = new WorkDetailVo(workSubmit1, workDemand1, user, announcer.getUserName());
                                } else {
                                    Admin correct = adminMapper.selectById(workSubmit1.getCorrectId());
                                    workDetailVo = new WorkDetailVo(workSubmit1, workDemand1, user, announcer.getUserName(), correct.getUserName());
                                }
                                workDetailVoList.add(workDetailVo);
                            }
                        }
                    }
                }
            }
        }else if(workDetailListRequest.getType()==20){
            User user = userMapper.selectById(workDetailListRequest.getId());
            if(Objects.isNull(user)){
                throw new BizException("用户不存在");
            }
            if(user.getStatus()!=10) {
                throw new BizException("用户状态异常");
            }
            if(!Objects.equals(user.getToken(), workDetailListRequest.getToken())){
                throw new BizException("未登陆或登陆超时");
            }
            QueryWrapper<WorkSubmit> workSubmitQueryWrapper = new QueryWrapper<>();
            WorkSubmit workSubmit0 = new WorkSubmit();
            workSubmit0.setStudentId(workDetailListRequest.getId());
            workSubmitQueryWrapper.setEntity(workSubmit0);
            List<WorkSubmit> workSubmitList = workSubmitMapper.selectList(workSubmitQueryWrapper);
            for(WorkSubmit workSubmit1 : workSubmitList){
                WorkDemand workDemand = workDemandMapper.selectById(workSubmit1.getDemandId());
                Admin announcer = adminMapper.selectById(workDemand.getAnnouncerId());
                if (Objects.isNull(announcer) || Objects.isNull(user)) {
                    throw new BizException("查询错误");
                }
                WorkDetailVo workDetailVo;
                if (Objects.isNull(workSubmit1.getCorrectId())) {
                    workDetailVo = new WorkDetailVo(workSubmit1, workDemand, user, announcer.getUserName());
                } else {
                    Admin correct = adminMapper.selectById(workSubmit1.getCorrectId());
                    workDetailVo = new WorkDetailVo(workSubmit1, workDemand, user, announcer.getUserName(), correct.getUserName());
                }
                workDetailVoList.add(workDetailVo);
            }
        }else{
            throw new BizException("用户类型错误");
        }
        return workDetailVoList;
    }
}
