package com.hebutgo.ework.entity.vo;

import com.hebutgo.ework.entity.GroupInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理/创建信息返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理/创建小组信息")
public class WorkGroupVo {

    @ApiModelProperty(value = "自增id")
    private Integer id;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    public WorkGroupVo(GroupInfo groupInfo) {
        this.id = groupInfo.getId();
        this.groupName = groupInfo.getGroupName();
    }

}
