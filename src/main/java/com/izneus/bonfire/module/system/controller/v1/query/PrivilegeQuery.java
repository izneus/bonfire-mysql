package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.module.system.entity.SysPrivilegeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: Joker
 * @Description: TODO
 * @DateTime: 2022-02-10 11:00
 **/
@ApiModel("权限Query")
@Data
public class PrivilegeQuery {

    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名不能为空")
    private String privName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("分类")
    private Integer privType;

    @ApiModelProperty("上级权限ID")
    private String parentId;
}
