package com.izneus.bonfire.module.system.controller.v1.vo;

import com.izneus.bonfire.common.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/12/31
 */
@ApiModel("工单列表VO")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ListTicketVO extends BasePageVO {
    @ApiModelProperty("通知列表")
    private List<TicketItemVO> tickets;
}
