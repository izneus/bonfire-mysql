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
 * 调度任务日志表
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SCHED_JOB_LOG")
public class SchedJobLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * sys_job表id
     */
    @TableField("JOB_ID")
    private String jobId;

    @TableField("JOB_CLASS")
    private String jobClass;

    @TableField("JOB_METHOD")
    private String jobMethod;

    @TableField("PARAM")
    private String param;

    /**
     * 任务执行状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 出错信息
     */
    @TableField("MESSAGE")
    private String message;

    /**
     * 执行消耗时间，单位毫秒
     */
    @TableField("DURATION_MILLIS")
    private Long durationMillis;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("CREATE_USER")
    private String createUser;

    @TableField("UPDATE_TIME")
    private Date updateTime;

    @TableField("UPDATE_USER")
    private String updateUser;


}
