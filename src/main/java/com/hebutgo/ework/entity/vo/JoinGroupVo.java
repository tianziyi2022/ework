package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理员/用户加入小组返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员/用户 加入/退出小组")
public class JoinGroupVo {

    @ApiModelProperty(value = "小组自增id")
    private Integer id;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "提示语")
    private String topic;

}
