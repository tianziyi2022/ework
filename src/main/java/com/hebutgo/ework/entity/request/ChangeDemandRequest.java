package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员修改作业要求请求参数")
public class ChangeDemandRequest {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "登陆令牌token")
    private String token;

    @ApiModelProperty(value = "作业要求自增id")
    private Integer demandId;

    @ApiModelProperty(value = "作业标题")
    private String title;

    @ApiModelProperty(value = "作业内容描述")
    private String description;

    @ApiModelProperty(value = "附件链接")
    private Integer appendixUrl;

}
