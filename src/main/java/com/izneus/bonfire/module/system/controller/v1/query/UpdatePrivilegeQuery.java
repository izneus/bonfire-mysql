package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Joker
 * @Description: TODO
 * @DateTime: 2022-02-10 14:18
 **/
@ApiModel("更新角色query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdatePrivilegeQuery extends PrivilegeQuery{
    @ApiModelProperty("权限id")
    @NotBlank
    private String id;
}
