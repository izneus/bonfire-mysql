package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.constant.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Izneus
 * @date 2020/06/29
 */
@ApiModel("登录表单")
@Data
public class LoginQuery {

    @ApiModelProperty(value = "用户名", required = true)
    @Pattern(regexp = RegExp.USERNAME, message = "用户名必须为6-20位字母或者数字")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @Pattern(regexp = RegExp.PASSWORD, message = "密码必须包含小写字母、大写字母和数字，长度为8～16")
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    @ApiModelProperty(value = "验证码id", required = true)
    @NotBlank(message = "验证码id不能为空")
    private String captchaId;

}
