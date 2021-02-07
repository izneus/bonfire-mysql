package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2021/02/07
 */
@Data
@ApiModel("缓存所有字段vo")
public class CacheDictVO {
    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("字典文本")
    private String dictLabel;
}
