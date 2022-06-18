package com.izneus.bonfire.module.bpm.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2022/05/26
 */
@ApiModel("流程实例详情vo")
@Data
@Builder
public class ProcessInstanceVO {

    @ApiModelProperty("实例id")
    private String id;

    @ApiModelProperty("实例xml")
    private String bpmnXml;

    @ApiModelProperty("活跃节点id列表")
    private List<String> activityIds;

}
