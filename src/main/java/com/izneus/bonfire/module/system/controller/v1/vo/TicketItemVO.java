package com.izneus.bonfire.module.system.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2020/12/31
 */
@ApiModel("工单列表ItemVO")
@Data
public class TicketItemVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("工单标题")
    private String title;

    @ApiModelProperty("工单内容")
    private String ticket;

    @ApiModelProperty("工单状态")
    private String status;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
