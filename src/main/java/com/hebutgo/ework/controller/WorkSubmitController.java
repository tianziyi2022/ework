package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.WorkSubmit;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.CreateDemandVo;
import com.hebutgo.ework.entity.vo.SubmitWorkVo;
import com.hebutgo.ework.entity.vo.WorkDetailVo;
import com.hebutgo.ework.service.IWorkSubmitService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 提交作业内容表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/ework/work-submit")
public class WorkSubmitController {

    Logger logger = LoggerFactory.getLogger(WorkSubmit.class);

    @Autowired
    IWorkSubmitService iWorkSubmitService;

    @CrossOrigin
    @ApiOperation(value = "用户完成并保存作业",tags = CommonConstant.WORK_SUBMIT)
    @PostMapping("/complete")
    public ApiResponse<SubmitWorkVo> complete(
            @RequestBody CompleteWorkRequest completeWorkRequest
    ){
        SubmitWorkVo submitWorkVo;
        try{
            submitWorkVo = iWorkSubmitService.complete(completeWorkRequest);
        }catch (BizException e) {
            logger.error("新建作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("新建作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("新建作业要求成功");
        return ApiResponse.success(submitWorkVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户提交已保存的作业",tags = CommonConstant.WORK_SUBMIT)
    @PostMapping("/submit")
    public ApiResponse<SubmitWorkVo> submit(
            @RequestBody SubmitWorkRequest submitWorkRequest
    ){
        SubmitWorkVo submitWorkVo;
        try{
            submitWorkVo = iWorkSubmitService.submit(submitWorkRequest);
        }catch (BizException e) {
            logger.error("用户提交已保存的作业失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户提交已保存的作业失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户提交已保存的作业成功");
        return ApiResponse.success(submitWorkVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户撤回已提交的作业",tags = CommonConstant.WORK_SUBMIT)
    @PostMapping("/withdraw")
    public ApiResponse<SubmitWorkVo> withdraw(
            @RequestBody SubmitWorkRequest submitWorkRequest
    ){
        SubmitWorkVo submitWorkVo;
        try{
            submitWorkVo = iWorkSubmitService.withdraw(submitWorkRequest);
        }catch (BizException e) {
            logger.error("用户撤回已提交的作业失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户撤回已提交的作业失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户撤回已提交的作业成功");
        return ApiResponse.success(submitWorkVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员退回用户提交的作业",tags = CommonConstant.WORK_CORRECT)
    @PostMapping("/returnWork")
    public ApiResponse<SubmitWorkVo> returnWork(
            @RequestBody ReturnWorkRequest returnWorkRequest
    ){
        SubmitWorkVo submitWorkVo;
        try{
            submitWorkVo = iWorkSubmitService.returnWork(returnWorkRequest);
        }catch (BizException e) {
            logger.error("管理员退回用户提交的作业失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员退回用户提交的作业失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员退回用户提交的作业成功");
        return ApiResponse.success(submitWorkVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员批改用户提交的作业",tags = CommonConstant.WORK_CORRECT)
    @PostMapping("/correct")
    public ApiResponse<SubmitWorkVo> correct(
            @RequestBody CorrectWorkRequest correctWorkRequest
            ){
        SubmitWorkVo submitWorkVo;
        try{
            submitWorkVo = iWorkSubmitService.correct(correctWorkRequest);
        }catch (BizException e) {
            logger.error("管理员批改用户提交的作业失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员批改用户提交的作业失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员批改用户提交的作业成功");
        return ApiResponse.success(submitWorkVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员重判用户提交的作业",tags = CommonConstant.WORK_CORRECT)
    @PostMapping("/recorrect")
    public ApiResponse<SubmitWorkVo> recorrect(
            @RequestBody CorrectWorkRequest correctWorkRequest
    ){
        SubmitWorkVo submitWorkVo;
        try{
            submitWorkVo = iWorkSubmitService.recorrect(correctWorkRequest);
        }catch (BizException e) {
            logger.error("管理员重判用户提交的作业失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员重判用户提交的作业失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员重判用户提交的作业成功");
        return ApiResponse.success(submitWorkVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员/用户查看作业详情",tags = CommonConstant.WORK_CORRECT)
    @PostMapping("/detail")
    public ApiResponse<WorkDetailVo> detail(
            @RequestBody WorkDetailRequest workDetailRequest
    ){
        WorkDetailVo workDetailVo;
        try{
            workDetailVo = iWorkSubmitService.detail(workDetailRequest);
        }catch (BizException e) {
            logger.error("管理员/用户查看作业详情失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员/用户查看作业详情失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员/用户查看作业详情成功");
        return ApiResponse.success(workDetailVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员/用户查看作业详情列表",tags = CommonConstant.WORK_CORRECT)
    @PostMapping("/detailList")
    public ApiResponse<List<WorkDetailVo>> detailList(
            @RequestBody WorkDetailListRequest workDetailListRequest
    ) {
        List<WorkDetailVo> workDetailVoList;
        try {
            workDetailVoList = iWorkSubmitService.detailList(workDetailListRequest);
        } catch (BizException e) {
            logger.error("管理员/用户查看作业详情列表失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员/用户查看作业详情列表失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员/用户查看作业详情列表成功");
        return ApiResponse.success(workDetailVoList);
    }

}
