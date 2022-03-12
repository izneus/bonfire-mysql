package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Joker
 * @date 2022-02-10 11:00
 **/
@ApiModel("权限Query")
@Data
public class PrivilegeQuery {

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限类型")
    @NotNull(message = "权限类型不能为空")
    private Integer privType;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名称不能为空")
    private String privName;

    @ApiModelProperty("权限字符串")
    @NotBlank(message = "权限字符串不能为空")
    private String privKey;

    @ApiModelProperty("父权限ID")
    @NotBlank(message = "父权限ID不能为空")
    private String parentId;

    @ApiModelProperty("排序号")
    private Integer sort;
}
