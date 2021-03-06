package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员发布作业要求请求参数")
public class AnnounceDemandRequest {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "登陆令牌token")
    private String token;

    @ApiModelProperty(value = "作业要求自增id")
    private Integer demandId;

    @ApiModelProperty(value = "开始（收作业）时间")
    private Timestamp startTime;

    @ApiModelProperty(value = "结束（收作业）时间")
    private Timestamp endTime;

    @ApiModelProperty(value = "开始（收作业）时间（整数格式）")
    private Double startTimeMills;

    @ApiModelProperty(value = "结束（收作业）时间（整数格式）")
    private Double endTimeMills;

    @ApiModelProperty(value = "发放小组id")
    private Integer groupId;
}
