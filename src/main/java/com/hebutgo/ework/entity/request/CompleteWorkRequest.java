package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "用户完成作业请求参数")
public class CompleteWorkRequest {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "登陆令牌token")
    private String token;

    @ApiModelProperty(value = "提交作业自增id")
    private Integer submitId;

    @ApiModelProperty(value = "作业内容")
    private String text;

    @ApiModelProperty(value = "附件1链接")
    private Integer appendixUrl1;

    @ApiModelProperty(value = "附件2链接")
    private Integer appendixUrl2;

    @ApiModelProperty(value = "附件3链接")
    private Integer appendixUrl3;

    @ApiModelProperty(value = "附件4链接")
    private Integer appendixUrl4;

    @ApiModelProperty(value = "附件5链接")
    private Integer appendixUrl5;
}
