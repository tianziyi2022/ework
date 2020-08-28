package com.hebutgo.ework.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hebutgo.ework.entity.WorkDemand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>
 *  管理员查看作业要求返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "管理员查看作业要求")
public class DemandDetailVo {

    @ApiModelProperty(value = "自增id")
    private Integer id;

    @ApiModelProperty(value = "作业号（新建时生成）")
    private String demandId;

    @ApiModelProperty(value = "发布者（管理员）id")
    private Integer announcerId;

    @ApiModelProperty(value = "作业标题")
    private String title;

    @ApiModelProperty(value = "作业内容描述")
    private String description;

    @ApiModelProperty(value = "附件链接")
    private Integer appendixUrl;

    @ApiModelProperty(value = "作业状态（110已发布，120修改后发布，0无效，10已保存未发布，20已修改未发布）")
    private Integer status;

    @ApiModelProperty(value = "开始（收作业）时间")
    private Timestamp startTime;

    @ApiModelProperty(value = "结束（收作业）时间")
    private Timestamp endTime;

    @ApiModelProperty(value = "发放学生计数")
    private Integer studentCount;

    @ApiModelProperty(value = "已提交数量")
    private Integer submitCount;

    @ApiModelProperty(value = "发放小组id")
    private Integer groupId;

    public DemandDetailVo(WorkDemand workDemand){
        this.id = workDemand.getId();
        this.demandId = workDemand.getDemandId();
        this.announcerId = workDemand.getAnnouncerId();
        this.title = workDemand.getTitle();
        this.description = workDemand.getDescription();
        this.appendixUrl = workDemand.getAppendixUrl();
        this.status = workDemand.getStatus();
        this.startTime = workDemand.getStartTime();
        this.endTime = workDemand.getEndTime();
        this.studentCount = workDemand.getStudentCount();
        this.submitCount = workDemand.getSubmitCount();
        this.groupId = workDemand.getGroupId();
    }
}
