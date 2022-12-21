package com.izneus.bonfire.module.dingtalk.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022-12-16
 */
@ApiModel("钉钉js签名vo")
@Data
@Builder
public class GetSignVO {

    @ApiModelProperty("应用agentId")
    private Long agentId;

    @ApiModelProperty("应用所在组织id")
    private String corpId;

    @ApiModelProperty("时间戳")
    private Long timeStamp;

    @ApiModelProperty("自定义字符串")
    private String nonceStr;

    @ApiModelProperty("签名")
    private String signature;

}
