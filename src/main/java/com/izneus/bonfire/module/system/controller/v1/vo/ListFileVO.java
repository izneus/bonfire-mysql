package com.izneus.bonfire.module.system.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2020/09/22
 */
@ApiModel("文件列表vo")
@Data
public class ListFileVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("文件后缀")
    private String suffix;

    @ApiModelProperty("文件大小")
    private String fileSize;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("备注")
    private String remark;
}
