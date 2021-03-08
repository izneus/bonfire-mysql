package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/09/01
 */
@ApiModel("设置角色权限query")
@Data
public class RoleAuthQuery {
    @ApiModelProperty("权限id列表")
    private List<String> authIds;
}
