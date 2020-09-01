package com.hebutgo.ework.controller;


import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.User;
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
        logger.info("用户注册成功");
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
        logger.info("用户登陆成功");
        return ApiResponse.success(loginVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户修改账户信息", tags = CommonConstant.USER_ACCOUNT)
    @PostMapping("/changeDetail")
    public ApiResponse<ChangeDetailVo> changeDetail(
            @RequestBody ChangeDetailRequest changeDetailRequest
    ) {
        ChangeDetailVo changeDetailVo;
        try {
            changeDetailVo = iUserService.changeDetail(changeDetailRequest);
        } catch (BizException e) {
            logger.error("修改账户信息失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("修改账户信息失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户修改账户信息成功");
        return ApiResponse.success(changeDetailVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户安全退出", tags = CommonConstant.USER_ACCOUNT)
    @PostMapping("/quit")
    public ApiResponse<LogoutVo> quit(
            @RequestBody LogoutRequest logoutRequest
    ) {
        LogoutVo logoutVo;
        try {
            logoutVo = iUserService.quit(logoutRequest);
        } catch (BizException e) {
            logger.error("用户安全退出失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户安全退出失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户安全退出成功");
        return ApiResponse.success(logoutVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户注销账号", tags = CommonConstant.USER_ACCOUNT)
    @PostMapping("/logout")
    public ApiResponse<LogoutVo> logout(
            @RequestBody LogoutRequest logoutRequest
    ) {
        LogoutVo logoutVo;
        try {
            logoutVo = iUserService.logout(logoutRequest);
        } catch (BizException e) {
            logger.error("用户注销账号失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户注销账号失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户注销账号成功");
        return ApiResponse.success(logoutVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户加入小组", tags = CommonConstant.USER_GROUP)
    @PostMapping("/joinGroup")
    public ApiResponse<JoinGroupVo> joinGroup(
            @RequestBody JoinGroupRequest joinGroupRequest
            ) {
        JoinGroupVo joinGroupVo;
        try {
            joinGroupVo = iUserService.joinGroup(joinGroupRequest);
        } catch (BizException e) {
            logger.error("用户加入小组失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户加入小组失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户加入小组成功");
        return ApiResponse.success(joinGroupVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户退出小组", tags = CommonConstant.USER_GROUP)
    @PostMapping("/quitGroup")
    public ApiResponse<JoinGroupVo> quitGroup(
            @RequestBody JoinGroupRequest joinGroupRequest
    ) {
        JoinGroupVo joinGroupVo;
        try {
            joinGroupVo = iUserService.quitGroup(joinGroupRequest);
        } catch (BizException e) {
            logger.error("用户退出小组失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("用户退出小组失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户退出小组成功");
        return ApiResponse.success(joinGroupVo);
    }

    @CrossOrigin
    @ApiOperation(value = "用户查看账户信息", tags = CommonConstant.USER_ACCOUNT)
    @PostMapping("/detail")
    public ApiResponse<UserDetailVo> detail(
            @RequestBody AccountDetailRequest accountDetailRequest
    ) {
        UserDetailVo userDetailVo;
        try {
            userDetailVo = iUserService.detail(accountDetailRequest);
        } catch (BizException e) {
            logger.error("修改账户信息失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("修改账户信息失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("用户查看账户信息成功");
        return ApiResponse.success(userDetailVo);
    }

}
