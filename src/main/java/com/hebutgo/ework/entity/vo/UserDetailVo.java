package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  用户查看个人信息返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "用户查看个人信息")
public class UserDetailVo {

    @ApiModelProperty(value = "学号")
    private String studentId;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "用户所在组")
    private Integer groupId;

    @ApiModelProperty(value = "用户所在组名称")
    private String groupName;

    @ApiModelProperty(value = "小组邀请码")
    private String groupCode;

    @ApiModelProperty(value = "用户状态（10可用，0已注销，20被锁定，其余异常）")
    private Integer status;

}
