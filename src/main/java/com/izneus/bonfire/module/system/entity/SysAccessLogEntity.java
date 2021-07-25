package com.izneus.bonfire.module.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_access_log")
public class SysAccessLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * http method
     */
    private String method;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 注解描述
     */
    private String description;

    /**
     * 发起请求的用户名
     */
    private String username;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 系统
     */
    private String os;

    /**
     * 发起请求的用户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 访问经过的时间
     */
    private BigDecimal elapsedTime;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 备注
     */
    private String remark;


}
