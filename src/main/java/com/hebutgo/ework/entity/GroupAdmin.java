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
 * 小组的管理员表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GroupAdmin对象", description="小组的管理员表")
public class GroupAdmin extends Model<GroupAdmin> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小组编号（小组表自增id）")
    private Integer groupId;

    @ApiModelProperty(value = "管理员编号（管理员表自增id）")
    private Integer adminId;

    @ApiModelProperty(value = "关系编号（系统自动生成）")
    private String code;

    @ApiModelProperty(value = "是否被删除（0未被删除/有效，1被删除/无效）")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
