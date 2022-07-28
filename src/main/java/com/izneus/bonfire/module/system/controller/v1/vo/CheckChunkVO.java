package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2022/07/17
 */
@ApiModel("分片校验vo")
@Data
@Builder
public class CheckChunkVO {
    @ApiModelProperty("已经上传的分片序号")
    private List<Long> uploadChunks;
}
