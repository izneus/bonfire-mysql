package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.constant.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Izneus
 * @date 2020/07/26
 */
@ApiModel("新增用户Query")
@Data
public class CreateUserQuery {

    @ApiModelProperty(value = "用户名", required = true)
    @Pattern(regexp = RegExp.USERNAME, message = "用户名必须为6-20位字母或者数字")
    private String username;

    @ApiModelProperty("备注")
    private String remark;
}
