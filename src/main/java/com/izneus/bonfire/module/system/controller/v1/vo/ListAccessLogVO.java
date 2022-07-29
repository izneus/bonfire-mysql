package com.izneus.bonfire.module.system.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2021-03-11
 */
@ApiModel("访问日志列表VO")
@Data
public class ListAccessLogVO {
    @ApiModelProperty("请求者用户名")
    private String username;

    @ApiModelProperty("请求者id")
    private String createUser;

    @ApiModelProperty("日志id")
    private String id;

    @ApiModelProperty("方法名称")
    private String method;

    @ApiModelProperty("用户代理")
    private String userAgent;

    @ApiModelProperty("客户端ip")
    private String clientIp;

    @ApiModelProperty("注解描述")
    private String description;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("系统")
    private String os;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("请求耗时，单位毫秒")
    private Long elapsedTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
