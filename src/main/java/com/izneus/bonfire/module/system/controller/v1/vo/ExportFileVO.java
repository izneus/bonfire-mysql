package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Izneus
 * @date 2021-01-14
 */
@Data
@ApiModel("导出文件vo")
@Builder
public class ExportFileVO {

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("token")
    private String token;

}
