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
 * 角色_权限
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_ROLE_AUTHORITY")
public class SysRoleAuthorityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * sys_role表id
     */
    @TableField("ROLE_ID")
    private String roleId;

    /**
     * sys_authority表id
     */
    @TableField("AUTHORITY_ID")
    private String authorityId;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 创建用户
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新用户
     */
    @TableField("UPDATE_USER")
    private Date updateUser;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;


}
