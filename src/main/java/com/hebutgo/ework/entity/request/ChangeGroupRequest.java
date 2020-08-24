package com.hebutgo.ework.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员修改小组信息请求参数")
public class ChangeGroupRequest {
    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "用户类型（10管理员admin，20普通用户user）")
    private Integer type;

    @ApiModelProperty(value = "登陆令牌token")
    private String token;

    @ApiModelProperty(value = "小组自增id")
    private Integer groupId;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "小组邀请码（创建小组时填写）")
    private String groupCode;

    @ApiModelProperty(value = "小组描述")
    private String description;

    @ApiModelProperty(value = "小组状态")
    private Integer status;
}
