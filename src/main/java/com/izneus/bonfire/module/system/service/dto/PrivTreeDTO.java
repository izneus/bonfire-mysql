package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2022-01-20
 */
@ApiModel("权限树dto")
@Data
public class PrivTreeDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("权限类型")
    private String privType;

    @ApiModelProperty("权限名称，中文")
    private String privName;

    @ApiModelProperty("权限key，英文")
    private String privKey;

    @ApiModelProperty("父权限id")
    private String parentId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("子权限")
    private List<PrivTreeDTO> children;

}
