package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/08/13
 */
@ApiModel("角色列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListRoleQuery extends BasePageQuery {
    @ApiModelProperty("角色名称或备注")
    private String query;
}
