package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@ApiModel("权限Query")
@Data
public class AuthQuery {
    @ApiModelProperty("权限id")
    private String id;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名不能为空")
    private String authority;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("分类")
    private String type;
}
