package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@ApiModel("权限列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListAuthQuery extends BasePageQuery {
    @ApiModelProperty("查询参数，模糊匹配权限名称或备注")
    private String query;

    @ApiModelProperty("权限类型")
    private String type;
}
