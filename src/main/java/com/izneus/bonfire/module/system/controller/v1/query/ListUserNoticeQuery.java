package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2021/3/22
 */
@ApiModel("用户通知query")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ListUserNoticeQuery extends BasePageQuery {
    @ApiModelProperty("通知状态")
    private String status;
}
