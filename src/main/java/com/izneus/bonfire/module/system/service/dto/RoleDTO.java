package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/18
 */
@ApiModel("新增角色query")
@Data
public class RoleDTO {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

///   权限一般都会比较多，暂时注释了
//    @ApiModelProperty("权限id数组")
//    private List<String> authorityIds;
}
