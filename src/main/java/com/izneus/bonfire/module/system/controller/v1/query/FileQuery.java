package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/09/24
 */
@ApiModel("更新文件query")
@Data
public class FileQuery {
    @ApiModelProperty("文件id")
    private String id;

    @ApiModelProperty("文件名称")
    private String filename;

    @ApiModelProperty("备注")
    private String remark;
}
