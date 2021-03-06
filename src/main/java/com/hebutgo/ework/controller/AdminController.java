package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.*;
import com.hebutgo.ework.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 管理员（教师）表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/ework/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(Admin.class);

    @Autowired
    IAdminService iAdminService;

    @CrossOrigin
    @ApiOperation(value = "管理员注册",tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/register")
    public ApiResponse<RegisterVo> register(
            @RequestBody AdminRegisterRequest adminRegisterRequest
    ){
        RegisterVo registerVo;
        try{
            registerVo = iAdminService.register(adminRegisterRequest);
        }catch (BizException e) {
            logger.error("管理员注册失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员注册失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员注册成功");
        return ApiResponse.success(registerVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员登陆", tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/login")
    public ApiResponse<LoginVo> login(
            @RequestBody LoginRequest loginRequest
    ) {
        LoginVo loginVo;
        try {
            loginVo = iAdminService.login(loginRequest);
        } catch (BizException e) {
            logger.error("登陆失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("登陆失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员登陆成功");
        return ApiResponse.success(loginVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员修改账户信息", tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/changeDetail")
    public ApiResponse<ChangeDetailVo> changeDetail(
            @RequestBody ChangeDetailRequest changeDetailRequest
            ) {
        ChangeDetailVo changeDetailVo;
        try {
            changeDetailVo = iAdminService.changeDetail(changeDetailRequest);
        } catch (BizException e) {
            logger.error("修改账户信息失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("修改账户信息失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员修改账户信息成功");
        return ApiResponse.success(changeDetailVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员安全退出", tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/quit")
    public ApiResponse<LogoutVo> quit(
            @RequestBody LogoutRequest logoutRequest
            ) {
        LogoutVo logoutVo;
        try {
            logoutVo = iAdminService.quit(logoutRequest);
        } catch (BizException e) {
            logger.error("管理员安全退出失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员安全退出失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员安全退出成功");
        return ApiResponse.success(logoutVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员注销账号", tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/logout")
    public ApiResponse<LogoutVo> logout(
            @RequestBody LogoutRequest logoutRequest
    ) {
        LogoutVo logoutVo;
        try {
            logoutVo = iAdminService.logout(logoutRequest);
        } catch (BizException e) {
            logger.error("管理员注销账号失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员注销账号失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员注销账号成功");
        return ApiResponse.success(logoutVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员查看账户信息", tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/detail")
    public ApiResponse<AdminDetailVo> detail(
            @RequestBody AccountDetailRequest accountDetailRequest
    ) {
        AdminDetailVo adminDetailVo;
        try {
            adminDetailVo = iAdminService.detail(accountDetailRequest);
        } catch (BizException e) {
            logger.error("管理员查看账户信息失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员查看账户信息失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员查看账户信息成功");
        return ApiResponse.success(adminDetailVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员获取管理小组信息（发布作业用）", tags = CommonConstant.ADMIN_ACCOUNT)
    @PostMapping("/groupList")
    public ApiResponse<List<WorkGroupVo>> groupList(
            @RequestBody AccountDetailRequest accountDetailRequest
    ) {
        List<WorkGroupVo> workGroupVoList;
        try {
            workGroupVoList = iAdminService.groupList(accountDetailRequest);
        } catch (BizException e) {
            logger.error("管理员获取管理小组信息（发布作业用）失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员获取管理小组信息（发布作业用）失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("管理员获取管理小组信息（发布作业用）成功");
        return ApiResponse.success(workGroupVoList);
    }
}
