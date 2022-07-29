package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020-08-14
 */
@AllArgsConstructor
@ApiModel("通用IdVO")
@Data
public class IdVO {
    @ApiModelProperty("id")
    private String id;
}
