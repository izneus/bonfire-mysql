package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.constant.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Izneus
 * @date 2021/08/01
 */
@Data
@ApiModel("修改密码query")
public class ChangePasswordQuery {

    @ApiModelProperty(value = "当前密码", required = true)
    @Pattern(regexp = RegExp.PASSWORD, message = "密码必须包含小写字母、大写字母和数字，长度为8～16")
    private String currentPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @Pattern(regexp = RegExp.PASSWORD, message = "密码必须包含小写字母、大写字母和数字，长度为8～16")
    private String newPassword;

    @ApiModelProperty(value = "确认密码", required = true)
    @Pattern(regexp = RegExp.PASSWORD, message = "密码必须包含小写字母、大写字母和数字，长度为8～16")
    private String confirmPassword;

}
