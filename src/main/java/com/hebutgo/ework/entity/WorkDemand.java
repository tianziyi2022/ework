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
 * 作业需求（发布作业内容）表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="WorkDemand对象", description="作业需求（发布作业内容）表")
public class WorkDemand extends Model<WorkDemand> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作业号（新建时生成）")
    private String demandId;

    @ApiModelProperty(value = "发布者（管理员）id")
    private Integer announcerId;

    @ApiModelProperty(value = "作业标题")
    private String title;

    @ApiModelProperty(value = "作业内容描述")
    private String description;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "附件链接")
    private Integer appendixUrl;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "作业状态（110已发布，120修改后发布，0无效，10已保存未发布，20已修改未发布）")
    private Integer status;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "开始（收作业）时间")
    private Timestamp startTime;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "结束（收作业）时间")
    private Timestamp endTime;

    @ApiModelProperty(value = "发放学生计数")
    private Integer studentCount;

    @ApiModelProperty(value = "已提交数量")
    private Integer submitCount;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    @ApiModelProperty(value = "发放小组id")
    private Integer groupId;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
