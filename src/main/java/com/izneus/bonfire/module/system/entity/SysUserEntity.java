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
 * 系统_用户
 * </p>
 *
 * @author Izneus
 * @since 2020-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_USER")
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("ID")
    private String id;

    /**
     * 用户名
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 全名
     */
    @TableField("FULLNAME")
    private String fullname;

    /**
     * 电子邮件
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 手机号码
     */
    @TableField("MOBILE")
    private String mobile;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 账号状态
     */
    @TableField("STATE")
    private String state;

    /**
     * 创建者的user_id
     */
    @TableField("CREATE_USER_ID")
    private String createUserId;


}
