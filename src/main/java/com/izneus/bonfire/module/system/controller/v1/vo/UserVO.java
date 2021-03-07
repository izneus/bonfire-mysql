package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/08/27
 */
@ApiModel("用户详情vo")
@Data
public class UserVO {
    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("全名")
    private String fullname;

    @ApiModelProperty("邮件")
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
