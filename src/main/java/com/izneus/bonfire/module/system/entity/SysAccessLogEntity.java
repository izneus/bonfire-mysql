package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统_访问日志
 * </p>
 *
 * @author Izneus
 * @since 2020-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_ACCESS_LOG")
public class SysAccessLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * http method
     */
    @TableField("METHOD")
    private String method;

    /**
     * 用户代理
     */
    @TableField("USER_AGENT")
    private String userAgent;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 客户端ip
     */
    @TableField("CLIENT_IP")
    private String clientIp;

    /**
     * 注解描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 发起请求的用户名
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 浏览器
     */
    @TableField("BROWSER")
    private String browser;

    /**
     * 系统
     */
    @TableField("OS")
    private String os;

    /**
     * 发起请求的用户id
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 访问经过的时间
     */
    @TableField("ELAPSED_TIME")
    private Long elapsedTime;

    /**
     * 请求参数
     */
    @TableField("PARAM")
    private String param;


}
