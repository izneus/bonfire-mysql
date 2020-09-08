package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Izneus
 * @date 2020/08/27
 */
@ApiModel("用户详情vo")
@Data
@EqualsAndHashCode(callSuper = true)
public class GetUserDTO extends UserDTO {
    @ApiModelProperty("用户id")
    private String id;
}
