package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理员或用户注册返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "注册")
public class RegisterVo {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

}