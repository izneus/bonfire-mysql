package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Joker
 * @Description: TODO
 * @DateTime: 2022-02-10 10:55
 **/
@ApiModel("权限详情vo")
@Data
public class PrivilegeVO {

    @ApiModelProperty("权限id")
    private String id;

    @ApiModelProperty("权限名称")
    private String privName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限类型")
    private String privType;

    @ApiModelProperty("父级ID")
    private String parentId;
}
