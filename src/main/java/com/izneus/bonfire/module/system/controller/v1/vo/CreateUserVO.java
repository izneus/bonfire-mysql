package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/07/26
 */
@ApiModel("新增用户VO")
@Data
@AllArgsConstructor
public class CreateUserVO {

    @ApiModelProperty("新建的用户id")
    private String id;
}
