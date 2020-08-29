package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员批改作业请求参数")
public class CorrectWorkRequest {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "登陆令牌token")
    private String token;

    @ApiModelProperty(value = "提交作业自增id")
    private Integer submitId;

    @ApiModelProperty(value = "评价分数（满分为100分）")
    private Integer score;

    @ApiModelProperty(value = "教师评语")
    private String comment;
}
