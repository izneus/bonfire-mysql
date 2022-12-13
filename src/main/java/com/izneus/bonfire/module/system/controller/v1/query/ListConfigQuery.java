package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2022-12-13
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("设置列表query")
@Data
public class ListConfigQuery extends BasePageQuery {
    @ApiModelProperty("设置key")
    private String cfgKey;
}
