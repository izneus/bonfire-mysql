package com.izneus.bonfire.module.quartz.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/11/04
 */
@ApiModel("调度任务列表ListJobQuery")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListJobQuery extends BasePageDTO {
    @ApiModelProperty("任务名")
    private String jobName;

    @ApiModelProperty("任务状态")
    private String status;
}
