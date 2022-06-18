package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2022/05/31
 */
@AllArgsConstructor
@ApiModel("通用IdsVO")
@Data
public class IdsVO {
    @ApiModelProperty("id")
    private List<String> ids;
}
