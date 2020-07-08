package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/06/29
 */
@ApiModel("登录返回信息")
@Data
public class LoginDTO {
    @ApiModelProperty("用户名")
    private final String username;

    @ApiModelProperty("jwt")
    private final String token;
}
