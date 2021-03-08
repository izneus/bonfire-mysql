package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/07/20
 */
@ApiModel("用户列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListUserQuery extends BasePageQuery {
    @ApiModelProperty("用户名")
    private String username;
}
