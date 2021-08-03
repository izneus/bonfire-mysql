package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2021/08/01
 */
@ApiModel("字典详情query")
@Data
public class DictTypeQuery {
    @ApiModelProperty("字典类型")
    private String dictType;
}
