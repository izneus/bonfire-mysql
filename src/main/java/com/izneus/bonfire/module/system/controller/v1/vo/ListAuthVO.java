package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/17
 */
@ApiModel("权限列表VO")
@Data
public class ListAuthVO {
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("权限")
    private String authority;
}
