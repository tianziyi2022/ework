package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  小组信息返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "小组信息")
public class GroupDetailVo {

    @ApiModelProperty(value = "自增id")
    private Integer id;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "小组邀请码（创建小组时填写）")
    private String groupCode;

    @ApiModelProperty(value = "创建者")
    private String createAdminName;

    @ApiModelProperty(value = "小组描述")
    private String descriptions;

    @ApiModelProperty(value = "状态（10未开放加入，20只允许管理员加入，30只允许学生加入，40允许管理员和学生加入，80停用，100已删除）")
    private Integer status;

}
