package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020-12-14
 */
@ApiModel("通知列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListNoticeQuery extends BasePageQuery {
    @ApiModelProperty("通知内容")
    private String query;
}
