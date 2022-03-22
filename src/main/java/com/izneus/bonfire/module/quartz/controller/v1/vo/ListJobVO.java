package com.izneus.bonfire.module.quartz.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2020/11/06
 */
@ApiModel("任务列表VO")
@Data
public class ListJobVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("任务名")
    private String jobName;
//
//    @ApiModelProperty("任务组")
//    private String jobGroup;
//
//    @ApiModelProperty("任务实现类")
//    private String jobClass;
//
//    @ApiModelProperty("任务实现方法")
//    private String jobMethod;

    @ApiModelProperty("cron表达式")
    private String cron;

//    @ApiModelProperty("任务参数")
//    private String param;

    @ApiModelProperty("任务状态")
    private String status;

//    @ApiModelProperty("备注")
//    private String remark;

    @ApiModelProperty("下次执行时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextRunTime;

    @ApiModelProperty("最近一次执行时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date prevRunTime;

    @ApiModelProperty("消耗时间，单位毫秒")
    private Long durationMillis;

}
