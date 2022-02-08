package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/08/18
 */
@ApiModel("角色vo")
@Data
public class RoleVO {

    @ApiModelProperty("角色id")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限id数组")
    private List<String> privilegeIds;
}
