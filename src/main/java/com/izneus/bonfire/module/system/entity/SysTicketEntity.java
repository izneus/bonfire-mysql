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
 * 系统工单表
 * </p>
 *
 * @author Izneus
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_TICKET")
public class SysTicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 工单标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 工单内容
     */
    @TableField("TICKET")
    private String ticket;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_USER")
    private String updateUser;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 工单状态
     */
    @TableField("STATUS")
    private String status;


}
