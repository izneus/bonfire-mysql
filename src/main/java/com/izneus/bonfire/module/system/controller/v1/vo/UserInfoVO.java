package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2021-07-22
 */
@Data
@ApiModel("登录后获得用户信息vo")
@Builder
public class UserInfoVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("fullname")
    private String fullname;

    @ApiModelProperty("username")
    private String username;

    @ApiModelProperty("权限列表，包含角色+权限字符串，半角逗号分隔")
    private List<String> roles;

}
