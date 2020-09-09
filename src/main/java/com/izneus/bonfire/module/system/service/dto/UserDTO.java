package com.izneus.bonfire.module.system.service.dto;

import com.izneus.bonfire.common.constant.RegExp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/07/26
 */
@ApiModel("新增用户Query")
@Data
public class UserDTO {
    @ApiModelProperty(value = "用户名", required = true)
    @Pattern(regexp = RegExp.USERNAME, message = "用户名必须为6-20位字母或者数字")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("全名")
    private String fullname;

    @ApiModelProperty("邮件")
    @Email
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("账号状态")
    private String state;

    @ApiModelProperty("角色id列表")
    private List<String> roleIds;
}
