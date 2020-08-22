package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理员或用户登陆返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "登陆")
public class LoginVo {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "登陆令牌token")
    private String token;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

}
