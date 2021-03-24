package com.izneus.bonfire.module.system.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.izneus.bonfire.common.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/12/31
 */
@ApiModel("工单列表VO")
@Data
public class ListTicketVO {

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
