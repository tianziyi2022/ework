package com.hebutgo.ework.controller;


import com.hebutgo.ework.entity.request.LoginRequest;
import com.hebutgo.ework.entity.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.UserRegisterRequest;
import com.hebutgo.ework.entity.vo.RegisterVo;
import com.hebutgo.ework.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/ework/user")
public class UserController {


    Logger logger = LoggerFactory.getLogger(User.class);

    @Autowired
    IUserService iUserService;

    @CrossOrigin
    @ApiOperation(value = "用户注册",tags = CommonConstant.USER_ACCOUNT)
    @PostMapping("/register")
    public ApiResponse register(
            @RequestBody UserRegisterRequest UserRegisterRequest
    ){
        RegisterVo registerVo;
        try{
            registerVo = iUserService.register(UserRegisterRequest);
        }catch (BizException e) {
            logger.error("用户注册失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户注册失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        return ApiResponse.success(registerVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户登陆", tags = CommonConstant.USER_ACCOUNT)
    @PostMapping("/login")
    public ApiResponse<LoginVo> login(
            @RequestBody LoginRequest loginRequest
    ) {
        LoginVo loginVo;
        try {
            loginVo = iUserService.login(loginRequest);
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
