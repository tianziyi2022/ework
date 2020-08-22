package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.request.AdminRegisterRequest;
import com.hebutgo.ework.entity.request.LoginRequest;
import com.hebutgo.ework.entity.vo.LoginVo;
import com.hebutgo.ework.entity.vo.RegisterVo;
import com.hebutgo.ework.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse register(
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
        return ApiResponse.success(loginVo);
    }
}
