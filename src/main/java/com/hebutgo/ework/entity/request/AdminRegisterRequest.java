package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员注册请求参数")
public class AdminRegisterRequest {

    @ApiModelProperty(value = "用户名")
    private String userId;

    @ApiModelProperty(value = "管理员学工号")
    private String adminId;

    @ApiModelProperty(value = "登陆密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String userName;

}
