package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员/用户登陆请求参数")
public class LoginRequest {

    @ApiModelProperty(value = "登录名userId/学工号/手机号")
    private String loginId;

    @ApiModelProperty(value = "登陆密码")
    private String password;

}
