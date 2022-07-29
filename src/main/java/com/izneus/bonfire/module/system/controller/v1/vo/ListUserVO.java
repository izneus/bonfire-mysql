package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020-07-29
 */
@ApiModel("用户列表vo")
@Data
public class ListUserVO {
    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("姓名")
    private String fullname;

    @ApiModelProperty("邮件")
    private String email;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("账号状态")
    private String status;
}
