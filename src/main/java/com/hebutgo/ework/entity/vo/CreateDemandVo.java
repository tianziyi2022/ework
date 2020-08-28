package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  管理员新建作业要求返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员新建作业要求")
public class CreateDemandVo {

    @ApiModelProperty(value = "作业要求自增id")
    private Integer id;

    @ApiModelProperty(value = "作业要求识别码（新建时系统生成）")
    private String demandId;

    @ApiModelProperty(value = "作业要求标题")
    private String title;

}
