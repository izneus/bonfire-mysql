package com.izneus.bonfire.module.bpm.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022/05/15
 */
@ApiModel("bpm模型metaInfo")
@Data
public class ModelMetaInfoDTO {
    @ApiModelProperty(value = "模型描述")
    private String description;
}
