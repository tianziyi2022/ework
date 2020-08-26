package com.hebutgo.ework.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 作业要求文件上传表
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="FileDemand对象", description="作业要求文件上传表")
public class FileDemand extends Model<FileDemand> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增id")
    private Integer id;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件链接")
    private String url;

    @ApiModelProperty(value = "是否被删除（0未删除/有效，1已删除/无效）")
    private Integer isDelete;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}