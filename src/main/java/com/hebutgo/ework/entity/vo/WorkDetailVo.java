package com.hebutgo.ework.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.WorkDemand;
import com.hebutgo.ework.entity.WorkSubmit;
import com.hebutgo.ework.mapper.WorkDemandMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * <p>
 *  用户/管理员查看作业详情返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "用户/管理员查看作业详情")
public class WorkDetailVo {

    @ApiModelProperty(value = "自增id")
    private Integer id;

    @ApiModelProperty(value = "提交作业号")
    private String submitId;

    @ApiModelProperty(value = "作业标题")
    private String title;

    @ApiModelProperty(value = "作业内容描述")
    private String description;

    @ApiModelProperty(value = "作业要求附件链接")
    private String appendixUrl;

    @ApiModelProperty(value = "发布者（管理员）姓名")
    private String announcerName;

    @ApiModelProperty(value = "学号")
    private String studentId;

    @ApiModelProperty(value = "昵称")
    private String userName;

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

    @ApiModelProperty(value = "批改教师姓名")
    private String correctName;

    @ApiModelProperty(value = "评价分数（满分为100分）")
    private Integer score;

    @ApiModelProperty(value = "教师评语")
    private String comment;

    @ApiModelProperty(value = "状态（0无效，100已发布未完成，110已完成保存未提交，120已撤回，210已提交，220已修改，230已批改，300作业要求已撤回）")
    private Integer status;

    @ApiModelProperty(value = "开始（收作业）时间")
    private Timestamp startTime;

    @ApiModelProperty(value = "结束（收作业）时间")
    private Timestamp endTime;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "修改时间")
    private Timestamp updateTime;

    public WorkDetailVo(WorkSubmit workSubmit, WorkDemand workDemand, User user, String announcerName, String correctName) {
        this.id = workSubmit.getId();
        this.submitId = workSubmit.getSubmitId();
        this.title = workDemand.getTitle();
        this.description = workDemand.getDescription();
//        this.appendixUrl = workDemand.getAppendixUrl();
        this.announcerName = announcerName;
        this.studentId = user.getStudentId();
        this.userName = user.getUserName();
        this.text = workSubmit.getText();
//        this.appendixUrl1 = workSubmit.getAppendixUrl1();
//        this.appendixUrl2 = workSubmit.getAppendixUrl2();
//        this.appendixUrl3 = workSubmit.getAppendixUrl3();
//        this.appendixUrl4 = workSubmit.getAppendixUrl4();
//        this.appendixUrl5 = workSubmit.getAppendixUrl5();
        this.correctName = correctName;
        this.score = workSubmit.getScore();
        this.comment = workSubmit.getComment();
        this.status = workSubmit.getStatus();
        this.startTime = workDemand.getStartTime();
        this.endTime = workDemand.getEndTime();
        this.createTime = workSubmit.getCreateTime();
        this.updateTime = workSubmit.getUpdateTime();
    }

    public WorkDetailVo(WorkSubmit workSubmit, WorkDemand workDemand, User user, String announcerName) {
        this.id = workSubmit.getId();
        this.submitId = workSubmit.getSubmitId();
        this.title = workDemand.getTitle();
        this.description = workDemand.getDescription();
//        this.appendixUrl = workDemand.getAppendixUrl();
        this.announcerName = announcerName;
        this.studentId = user.getStudentId();
        this.userName = user.getUserName();
        this.text = workSubmit.getText();
//        this.appendixUrl1 = workSubmit.getAppendixUrl1();
//        this.appendixUrl2 = workSubmit.getAppendixUrl2();
//        this.appendixUrl3 = workSubmit.getAppendixUrl3();
//        this.appendixUrl4 = workSubmit.getAppendixUrl4();
//        this.appendixUrl5 = workSubmit.getAppendixUrl5();
        this.status = workSubmit.getStatus();
        this.startTime = workDemand.getStartTime();
        this.endTime = workDemand.getEndTime();
        this.createTime = workSubmit.getCreateTime();
        this.updateTime = workSubmit.getUpdateTime();
    }
}
