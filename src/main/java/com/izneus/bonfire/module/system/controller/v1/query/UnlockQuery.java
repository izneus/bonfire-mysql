package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.constant.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Iznejus
 * @date 2021/03/07
 */
@ApiModel("解锁密码重试锁定用户query")
@Data
public class UnlockQuery {
    @ApiModelProperty(value = "用户名", required = true)
    @Pattern(regexp = RegExp.USERNAME, message = "用户名必须为6-20位字母或者数字")
    private String username;
}
