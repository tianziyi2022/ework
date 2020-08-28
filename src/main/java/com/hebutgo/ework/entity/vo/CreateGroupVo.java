package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理员新建组/修改组返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "新建组/修改小组信息")
public class CreateGroupVo {

    @ApiModelProperty(value = "小组自增id")
    private Integer id;

    @ApiModelProperty(value = "小组识别码（注册时系统生成）")
    private String groupId;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "小组邀请码（创建小组时填写）")
    private String groupCode;

}
