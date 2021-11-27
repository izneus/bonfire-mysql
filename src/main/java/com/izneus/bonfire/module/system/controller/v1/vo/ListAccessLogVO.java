package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2021/03/11
 */
@ApiModel("访问日志列表VO")
@Data
public class ListAccessLogVO {
    @ApiModelProperty("请求者用户名")
    private String username;

    @ApiModelProperty("请求者id")
    private String createUser;

    @ApiModelProperty("客户端ip")
    private String clientIp;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("系统")
    private String os;

    @ApiModelProperty("请求耗时,单位毫秒")
    private Long elapsedTime;
}
