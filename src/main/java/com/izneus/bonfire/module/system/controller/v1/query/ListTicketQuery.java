package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/12/31
 */
@ApiModel("工单列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListTicketQuery extends BasePageDTO {
    @ApiModelProperty("工单内容")
    private String query;

    @ApiModelProperty("排序，默认为create_time desc")
    private String orderBy;
}
