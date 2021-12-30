package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/09/01
 */
@ApiModel("角色设置权限query")
@Data
public class RoleAuthQuery {
    @ApiModelProperty("角色id")
    @NotBlank
    private String roleId;

    @ApiModelProperty("权限id列表")
    @NotNull
    @Size(min = 1, max = 500, message = "id列表最小长度为1，最大长度为500")
    private List<String> authorityIds;
}
