package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@ApiModel("权限dto")
@Data
public class AuthDTO {
    @ApiModelProperty("权限名称")
    private String authority;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("分类")
    private String type;
}
