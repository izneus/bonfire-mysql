package com.izneus.bonfire.module.quartz.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/11/06
 */
@ApiModel("任务列表ItemVO")
@Data
class JobItemVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("任务名")
    private String jobName;

    @ApiModelProperty("任务组")
    private String jobGroup;

    @ApiModelProperty("任务实现类")
    private String jobClass;

    @ApiModelProperty("任务实现方法")
    private String jobMethod;

    @ApiModelProperty("cron表达式")
    private String cron;

    @ApiModelProperty("任务参数")
    private String param;

    @ApiModelProperty("任务状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
