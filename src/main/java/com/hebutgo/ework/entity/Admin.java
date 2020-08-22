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
 * 管理员（教师）表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Admin对象", description="管理员（教师）表")
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userId;

    @ApiModelProperty(value = "管理员学工号")
    private String adminId;

    @ApiModelProperty(value = "登陆密码")
    private String password;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "昵称")
    private String userName;

    @ApiModelProperty(value = "管理员状态（10可用，0已注销，20被锁定，其余异常）")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "登陆令牌")
    private String token;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
