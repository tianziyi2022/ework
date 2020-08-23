package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理员或用户安全退出返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "登陆")
public class LogoutVo {

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "提示语")
    private String topic;
}
