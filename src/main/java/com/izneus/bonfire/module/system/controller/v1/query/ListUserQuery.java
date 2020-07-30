package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.util.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Izneus
 * @date 2020/07/20
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户列表")
@Data
public class ListUserQuery extends BasePageDTO {
    @ApiModelProperty("用户名")
    private String username;
}
