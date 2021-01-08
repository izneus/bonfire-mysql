package com.izneus.bonfire.module.system.controller.v1.vo;

import com.izneus.bonfire.module.system.service.dto.FlowDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/1/5
 */
@Data
@ApiModel("工单详情vo")
public class TicketVO {
    @ApiModelProperty("工单id")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("工单内容")
    private String ticket;

    @ApiModelProperty("工单状态")
    private String status;

    @ApiModelProperty("工单流")
    private List<FlowDTO> flows;
}
