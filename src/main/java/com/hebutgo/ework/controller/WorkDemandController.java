package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.WorkDemand;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.CreateDemandVo;
import com.hebutgo.ework.entity.vo.DemandDetailVo;
import com.hebutgo.ework.service.IWorkDemandService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 作业需求（发布作业内容）表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/ework/work-demand")
public class WorkDemandController {

    Logger logger = LoggerFactory.getLogger(WorkDemand.class);

    @Autowired
    IWorkDemandService iWorkDemandService;

    @CrossOrigin
    @ApiOperation(value = "管理员新建作业要求",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/create")
    public ApiResponse<CreateDemandVo> create(
            @RequestBody CreateDemandRequest createDemandRequest
            ){
        CreateDemandVo createDemandVo;
        try{
            createDemandVo = iWorkDemandService.create(createDemandRequest);
        }catch (BizException e) {
            logger.error("新建作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("新建作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("新建作业要求成功");
        return ApiResponse.success(createDemandVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员修改作业要求",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/change")
    public ApiResponse<CreateDemandVo> change(
            @RequestBody ChangeDemandRequest changeDemandRequest
            ){
        CreateDemandVo createDemandVo;
        try{
            createDemandVo = iWorkDemandService.change(changeDemandRequest);
        }catch (BizException e) {
            logger.error("新建作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("新建作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("新建作业要求成功");
        return ApiResponse.success(createDemandVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员发布作业要求",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/announce")
    public ApiResponse<CreateDemandVo> announce(
            @RequestBody AnnounceDemandRequest announceDemandRequest
    ){
        CreateDemandVo createDemandVo;
        try{
            createDemandVo = iWorkDemandService.announce(announceDemandRequest);
        }catch (BizException e) {
            logger.error("发布作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("发布作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("发布作业要求成功");
        return ApiResponse.success(createDemandVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员删除作业要求",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/delete")
    public ApiResponse<CreateDemandVo> delete(
            @RequestBody DeleteDemandRequest deleteDemandRequest
    ){
        CreateDemandVo createDemandVo;
        try{
            createDemandVo = iWorkDemandService.delete(deleteDemandRequest);
        }catch (BizException e) {
            logger.error("删除作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("删除作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("删除作业要求成功");
        return ApiResponse.success(createDemandVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员撤回作业要求",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/withdraw")
    public ApiResponse<CreateDemandVo> withdraw(
            @RequestBody DeleteDemandRequest deleteDemandRequest
    ){
        CreateDemandVo createDemandVo;
        try{
            createDemandVo = iWorkDemandService.withdraw(deleteDemandRequest);
        }catch (BizException e) {
            logger.error("撤回作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("撤回作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("撤回作业要求成功");
        return ApiResponse.success(createDemandVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员查看某个作业要求",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/detail")
    public ApiResponse<DemandDetailVo> detail(
            @RequestBody DemandDetailRequest demandDetailRequest
    ){
        DemandDetailVo demandDetailVo;
        try{
            demandDetailVo = iWorkDemandService.detail(demandDetailRequest);
        }catch (BizException e) {
            logger.error("查看某个作业要求失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("查看某个作业要求失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("查看某个作业要求成功");
        return ApiResponse.success(demandDetailVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员查看作业要求列表",tags = CommonConstant.WORK_DEMAND)
    @PostMapping("/detailList")
    public ApiResponse<List<DemandDetailVo>> detailList(
            @RequestBody DemandDetailListRequest demandDetailListRequest
    ){
        List<DemandDetailVo> demandDetailVoList;
        try{
            demandDetailVoList = iWorkDemandService.detailList(demandDetailListRequest);
        }catch (BizException e) {
            logger.error("查看作业要求列表失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("查看作业要求列表失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("查看作业要求列表成功");
        return ApiResponse.success(demandDetailVoList);
    }
}
