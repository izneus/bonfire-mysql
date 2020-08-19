package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/07/26
 */
@ApiModel("用户dto")
@Data
public class ListUserDTO {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("邮件")
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("账号状态")
    private String state;
}
