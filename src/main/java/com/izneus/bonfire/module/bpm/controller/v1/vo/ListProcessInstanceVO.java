package com.izneus.bonfire.module.bpm.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2022/05/09
 */
@ApiModel("流程实例列表vo")
@Data
public class ListProcessInstanceVO {

    @ApiModelProperty(value = "流程实例id")
    private String id;

    @ApiModelProperty(value = "流程名称")
    private String name;

    @ApiModelProperty(value = "发起人id")
    private String startUserId;

    @ApiModelProperty(value = "发起人username")
    private String startUser;

    @ApiModelProperty(value = "发起时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /*@ApiModelProperty(value = "当前节点")
    private String currentTask;*/

    @ApiModelProperty(value = "是否已结束")
    private Boolean isEnded;

}
