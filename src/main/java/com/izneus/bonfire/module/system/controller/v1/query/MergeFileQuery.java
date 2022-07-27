package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022/07/27
 */
@ApiModel("合并文件分片query")
@Data
public class MergeFileQuery {

    @ApiModelProperty("文件唯一id")
    private String identifier;

    @ApiModelProperty("文件名")
    private String filename;

}
