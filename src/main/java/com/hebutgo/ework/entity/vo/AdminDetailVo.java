package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *  管理员查看个人信息返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员查看个人信息")
public class AdminDetailVo {

    @ApiModelProperty(value = "管理员学工号")
    private String adminId;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "创建小组数量")
    private Integer createGroupCount;

    @ApiModelProperty(value = "创建小组信息")
    private List<GroupDetailVo> createGroupDetailVoList;

    @ApiModelProperty(value = "管理小组数量")
    private Integer adminGroupCount;

    @ApiModelProperty(value = "管理小组信息")
    private List<GroupDetailVo> adminGroupDetailVoList;

}
