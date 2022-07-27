package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Izneus
 * @date 2022/07/27
 */
@ApiModel("文件分片query")
@Data
public class ChunkQuery {

    @ApiModelProperty("分片序号")
    private Long chunkNumber;

    @ApiModelProperty("分片大小")
    private BigDecimal chunkSize;

    @ApiModelProperty("当前分片实际大小")
    private BigDecimal currentChunkSize;

    @ApiModelProperty("总大小")
    private Long totalSize;

    @ApiModelProperty("唯一id")
    private String identifier;

    @ApiModelProperty("文件名")
    private String filename;

    @ApiModelProperty("相对文件夹路径")
    private String relativePath;

    @ApiModelProperty("总分片数")
    private Long totalChunks;

}