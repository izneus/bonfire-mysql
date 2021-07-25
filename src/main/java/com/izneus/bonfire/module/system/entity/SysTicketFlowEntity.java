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
 * 工单回复信息
 * </p>
 *
 * @author Izneus
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_ticket_flow")
public class SysTicketFlowEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * sys_ticket.id
     */
    private String ticketId;

    /**
     * 回复信息
     */
    private String flow;

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
