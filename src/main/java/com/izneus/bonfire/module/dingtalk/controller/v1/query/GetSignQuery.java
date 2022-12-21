package com.izneus.bonfire.module.dingtalk.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2022-12-19
 */
@Data
@ApiModel("获取钉钉签名query")
public class GetSignQuery {
    @ApiModelProperty("页面url")
    @NotBlank
    private String url;
}
