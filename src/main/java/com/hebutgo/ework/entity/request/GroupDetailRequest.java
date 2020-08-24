package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "查看小组信息请求参数")
public class GroupDetailRequest {

    @ApiModelProperty(value = "小组自增id，未知传0")
    private Integer id;

    @ApiModelProperty(value = "小组识别码（注册时系统生成）")
    private String groupId;

    @ApiModelProperty(value = "小组邀请码（创建小组时填写）")
    private String groupCode;

}
