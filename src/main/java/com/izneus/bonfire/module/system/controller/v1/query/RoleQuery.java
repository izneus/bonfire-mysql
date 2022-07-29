package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Izneus
 * @date 2020-08-18
 */
@ApiModel("新增角色query")
@Data
public class RoleQuery {

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名不能为空")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限id数组")
    private List<String> privilegeIds;

}
