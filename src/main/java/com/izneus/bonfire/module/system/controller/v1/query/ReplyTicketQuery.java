package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2021/01/08
 */
@Data
@ApiModel("回复工单query")
public class ReplyTicketQuery {

    @ApiModelProperty(value = "工单id", required = true)
    @NotBlank(message = "工单id不能为空")
    private String ticketId;

    @ApiModelProperty(value = "回复内容", required = true)
    @NotBlank(message = "回复内容不能为空")
    private String flow;
}
