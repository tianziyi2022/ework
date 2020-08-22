package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel
public class UserRegisterRequest {

    @ApiModelProperty(value = "用户名")
    private String userId;

    @ApiModelProperty(value = "学号")
    private String studentId;

    @ApiModelProperty(value = "登陆密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String userName;

}
