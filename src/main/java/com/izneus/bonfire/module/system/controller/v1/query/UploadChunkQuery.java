package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Izneus
 * @date 2022/07/27
 */
@ApiModel("上传文件分片query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UploadChunkQuery extends ChunkQuery {
    @ApiModelProperty("文件")
    private MultipartFile file;
}
