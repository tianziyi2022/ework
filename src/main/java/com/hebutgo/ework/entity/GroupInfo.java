package com.hebutgo.ework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 小组信息表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GroupInfo对象", description="小组信息表")
public class GroupInfo extends Model<GroupInfo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小组识别码（注册时系统生成）")
    private String groupId;

    @ApiModelProperty(value = "小组名称")
    private String groupName;

    @ApiModelProperty(value = "小组邀请码（创建小组时填写）")
    private String groupCode;

    @ApiModelProperty(value = "创建者")
    private Integer createAdmin;

    @ApiModelProperty(value = "小组描述")
    private String descriptions;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "状态（0未开放加入，10只允许管理员加入，20只允许学生加入，30允许管理员和学生加入）")
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
