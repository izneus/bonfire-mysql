package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统_角色_权限关联表
 * </p>
 *
 * @author Izneus
 * @since 2022-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_privilege")
public class SysRolePrivilegeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid，pk
     */
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateUser;

    private String remark;

    /**
     * sys_role.id
     */
    private String roleId;

    /**
     * sys_privilege.id
     */
    private String privId;


}
