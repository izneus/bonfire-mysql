package com.izneus.bonfire.module.bpm.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022/05/09
 */
@ApiModel("bpm模型query")
@Data
public class BpmModelQuery {

    @ApiModelProperty(value = "流程名称")
    private String name;

    @ApiModelProperty(value = "流程key")
    private String key;

    @ApiModelProperty(value = "流程描述")
    private String description;

    @ApiModelProperty(value = "BPMN XML")
    private String bpmnXml;

}
