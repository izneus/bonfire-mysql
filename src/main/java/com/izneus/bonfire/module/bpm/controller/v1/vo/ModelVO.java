package com.izneus.bonfire.module.bpm.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022/05/26
 */
@ApiModel("模型详情vo")
@Data
@Builder
public class ModelVO {

    @ApiModelProperty("模型id")
    private String id;

    @ApiModelProperty(value = "BPMN XML")
    private String bpmnXml;

}
