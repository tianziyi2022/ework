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
 * 用户表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="用户表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userId;

    @ApiModelProperty(value = "学号")
    private String studentId;

    @ApiModelProperty(value = "登陆密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "用户所在组")
    private Integer groupId;

    @ApiModelProperty(value = "用户状态（10可用，0已注销，20被锁定，其余异常）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "登陆令牌")
    private String token;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
