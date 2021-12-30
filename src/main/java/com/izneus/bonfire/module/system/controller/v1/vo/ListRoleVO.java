package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@ApiModel("角色列表vo")
@Data
public class ListRoleVO {

    @ApiModelProperty("角色id")
    private String id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限id数组")
    private List<String> authorityIds;
}
