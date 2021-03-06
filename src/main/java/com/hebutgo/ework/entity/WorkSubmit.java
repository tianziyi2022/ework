package com.hebutgo.ework.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;
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
 * @since 2020-08-26
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

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "附件1链接")
    private Integer appendixUrl1;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "附件2链接")
    private Integer appendixUrl2;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "附件3链接")
    private Integer appendixUrl3;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "附件4链接")
    private Integer appendixUrl4;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "附件5链接")
    private Integer appendixUrl5;

    @ApiModelProperty(value = "备注")
    private String note;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "批改教师id")
    private Integer correctId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "评价分数（满分为100分）")
    private Integer score;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "教师评语")
    private String comment;

    @ApiModelProperty(value = "状态（0无效，100已发布未完成，110已完成保存未提交，120已撤回，210已提交，220已修改，230已批改，300作业要求已撤回）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
