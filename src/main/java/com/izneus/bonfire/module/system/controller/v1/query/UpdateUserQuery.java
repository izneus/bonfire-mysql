package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2021/10/28
 */
@ApiModel("用户详情vo")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateUserQuery extends UserQuery {
    @ApiModelProperty("用户id")
    @NotBlank
    private String id;
}
