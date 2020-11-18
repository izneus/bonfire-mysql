package com.izneus.bonfire.module.quartz.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2020/11/12
 */
@ApiModel("任务列表ItemVO")
@Data
public class LogItemVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("任务id")
    private String jobId;

    @ApiModelProperty("任务类")
    private String jobClass;

    @ApiModelProperty("任务方法")
    private String jobMethod;

    @ApiModelProperty("参数")
    private String param;

    @ApiModelProperty("任务状态")
    private String status;

    @ApiModelProperty("执行信息")
    private String message;

    @ApiModelProperty("持续时间")
    private String durationMillis;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
