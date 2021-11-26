package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/17
 */
@ApiModel("权限列表VO")
@Data
public class ListAuthVO {
    @ApiModelProperty("权限id")
    private String id;

    @ApiModelProperty("权限")
    private String authority;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限类型")
    private String type;
}
