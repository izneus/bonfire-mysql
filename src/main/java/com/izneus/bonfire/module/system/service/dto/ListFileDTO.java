package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date
 */
@ApiModel("文件列表dto")
@Data
public class ListFileDTO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("文件大小")
    private Long fileSize;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("创建人")
    private String createUser;
}
