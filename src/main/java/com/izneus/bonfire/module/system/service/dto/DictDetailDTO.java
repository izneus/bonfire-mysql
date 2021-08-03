package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2021/08/01
 */
@Data
@ApiModel("字典详情dto")
public class DictDetailDTO {
    @ApiModelProperty("字典值(编码)")
    private String dictValue;

    @ApiModelProperty("字典标签(中文)")
    private String dictLabel;
}
