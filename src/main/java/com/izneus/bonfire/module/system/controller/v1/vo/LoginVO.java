package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/06/29
 */
@ApiModel("登录返回信息")
@Data
@Builder
public class LoginVO {
    @ApiModelProperty("用户名")
    private final String username;

    @ApiModelProperty("jwt")
    private final String token;
}
