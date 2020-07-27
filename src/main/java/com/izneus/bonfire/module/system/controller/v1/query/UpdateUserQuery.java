package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/07/26
 */
@ApiModel("更新用户Query")
@Data
public class UpdateUserQuery {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("备注")
    private String remark;
}
