package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: Joker
 * @Description:
 * @DateTime: 2022-02-10 9:53
 **/
@ApiModel("权限列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListPrivilegeQuery extends BasePageQuery {
    @ApiModelProperty("权限名称")
    private String privName;

}
