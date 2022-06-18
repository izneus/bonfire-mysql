package com.izneus.bonfire.module.bpm.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022/05/09
 */
@ApiModel("bpm模型vo")
@Data
public class ListModelVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "部署id")
    private String deploymentId;

    @ApiModelProperty(value = "流程名称")
    private String name;

    @ApiModelProperty(value = "流程标志")
    private String key;

    @ApiModelProperty(value = "流程描述")
    private String description;

    @ApiModelProperty(value = "版本")
    private Integer version;

}
