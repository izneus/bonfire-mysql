package com.izneus.bonfire.module.dingtalk.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022-12-10
 */
@ApiModel("钉钉免登query")
@Data
public class DdLoginQuery {

    @ApiModelProperty("免登code")
    private String code;

}
