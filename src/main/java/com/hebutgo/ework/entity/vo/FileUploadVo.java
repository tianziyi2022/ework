package com.hebutgo.ework.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *  上传文件返回类
 * </p>
 *
 * @author tianziyi
 */
@Data
@ApiModel(value = "上传文件")
public class FileUploadVo {

    @ApiModelProperty(value = "文件id")
    private Integer id;

    @ApiModelProperty(value = "附件链接")
    private String url;

    @ApiModelProperty(value = "提示语")
    private String topic;

}
