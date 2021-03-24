package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author sunhz
 */
@ApiModel("用户工单query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListUserTicketQuery extends BasePageQuery {
}
