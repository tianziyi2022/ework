package com.hebutgo.ework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 提交作业内容表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WorkSubmit对象", description="提交作业内容表")
public class WorkSubmit extends Model<WorkSubmit> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "提交作业号")
    private String submitId;

    @ApiModelProperty(value = "作业需求（题目）id")
    private Integer demandId;

    @ApiModelProperty(value = "发布者（管理员）id")
    private Integer announcerId;

    @ApiModelProperty(value = "用户（完成者）id")
    private Integer studentId;

    @ApiModelProperty(value = "作业内容")
    private String text;

    @ApiModelProperty(value = "附件1链接")
    private String appendixUrl1;

    @ApiModelProperty(value = "附件2链接")
    private String appendixUrl2;

    @ApiModelProperty(value = "附件3链接")
    private String appendixUrl3;

    @ApiModelProperty(value = "附件4链接")
    private String appendixUrl4;

    @ApiModelProperty(value = "附件5链接")
    private String appendixUrl5;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "批改教师id")
    private Integer correctId;

    @ApiModelProperty(value = "评价分数（满分为100分）")
    private Integer score;

    @ApiModelProperty(value = "教师评语")
    private String comment;

    @ApiModelProperty(value = "状态（0无效，100已发布未完成，110已提交，120已修改，200已批改）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
