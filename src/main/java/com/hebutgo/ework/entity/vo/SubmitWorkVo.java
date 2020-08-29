package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  用户完成（保存）作业/提交作业/撤回作业返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "用户完成（保存）作业/提交作业/撤回作业要求")
public class SubmitWorkVo {

    @ApiModelProperty(value = "作业自增id")
    private Integer id;

    @ApiModelProperty(value = "作业要求标题")
    private String title;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

}
