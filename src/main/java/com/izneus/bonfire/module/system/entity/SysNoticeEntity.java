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
 * 系统通知表
 * </p>
 *
 * @author Izneus
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_NOTICE")
public class SysNoticeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private String id;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 通知内容
     */
    @TableField("NOTICE")
    private String notice;

    /**
     * 创建者
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新者
     */
    @TableField("UPDATE_USER")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;

    /**
     * 通知类型，字典，0=全局通知，1=局部消息
     */
    @TableField("NOTICE_TYPE")
    private String noticeType;


}
