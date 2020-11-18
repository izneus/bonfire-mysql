package com.izneus.bonfire.module.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 调度任务表
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SCHED_JOB")
public class SchedJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * 任务名称
     */
    @TableField("JOB_NAME")
    private String jobName;

    /**
     * 任务组
     */
    @TableField("JOB_GROUP")
    private String jobGroup;

    /**
     * 任务具体实现的方法名
     */
    @TableField("JOB_METHOD")
    private String jobMethod;

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
     * cron表达式
     */
    @TableField("CRON")
    private String cron;

    /**
     * 任务状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 任务具体实现的类名
     */
    @TableField("JOB_CLASS")
    private String jobClass;

    /**
     * 调用任务参数
     */
    @TableField("PARAM")
    private String param;


}
