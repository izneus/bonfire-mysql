package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2021/02/18
 */
@Data
@ApiModel("认证用户信息vo")
public class AuthUserVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("用户名")
    private String username;
}
