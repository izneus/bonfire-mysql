package com.izneus.bonfire.module.quartz.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2020/11/03
 */
@ApiModel("新建调度任务JobQuery")
@Data
public class JobQuery {
    @ApiModelProperty("任务名")
    @NotBlank(message = "任务名不能为空")
    private String jobName;

    @ApiModelProperty("任务组")
    @NotBlank(message = "任务组不能为空")
    private String jobGroup;

    @ApiModelProperty("任务实现类")
    @NotBlank(message = "任务实现类不能为空")
    private String jobClass;

    @ApiModelProperty("任务实现方法")
    @NotBlank(message = "任务实现方法不能为空")
    private String jobMethod;

    @ApiModelProperty("cron表达式")
    @NotBlank(message = "cron表达式不能为空")
    private String cron;

    @ApiModelProperty("任务参数")
    private String param;

    @ApiModelProperty("任务状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
