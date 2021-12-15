package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统_角色
 * </p>
 *
 * @author Izneus
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

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
