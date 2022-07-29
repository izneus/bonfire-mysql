package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2021-01-06
 */
@Data
@ApiModel("新增工单query")
public class TicketQuery {
    @ApiModelProperty(value = "工单标题", required = true)
    @NotBlank(message = "工单标题不能为空")
    private String title;

    @ApiModelProperty(value = "工单内容", required = true)
    @NotBlank(message = "工单内容不能为空")
    private String ticket;
}
