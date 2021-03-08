package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/18
 */
@ApiModel("权限详情vo")
@Data
public class AuthVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("权限名称")
    private String authority;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("类型")
    private String type;
}
