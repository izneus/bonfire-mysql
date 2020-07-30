package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/07/27
 */
@Data
@ApiModel("获得用户vo")
public class GetUserVO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("备注")
    private String remark;

}
