package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2022-12-13
 */
@ApiModel("设置query")
@Data
public class ConfigQuery {

    @ApiModelProperty("key")
    @NotBlank(message = "configKey不能为空")
    private String cfgKey;

    @ApiModelProperty("value")
    private String cfgValue;

    @ApiModelProperty("备注")
    private String remark;

}
