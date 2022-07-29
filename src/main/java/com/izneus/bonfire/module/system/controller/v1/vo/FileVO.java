package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2020-09-23
 */
@ApiModel("文件详情vo")
@Data
public class FileVO {
    @ApiModelProperty("文件id")
    private String id;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("备注")
    private String remark;
}
