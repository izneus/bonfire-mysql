package com.izneus.bonfire.module.system.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2021-01-06
 */
@Data
@ApiModel("工单流DTO")
public class FlowDTO {
    @ApiModelProperty("工单流id")
    private String id;

    @ApiModelProperty("工单流内容")
    private String flow;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
