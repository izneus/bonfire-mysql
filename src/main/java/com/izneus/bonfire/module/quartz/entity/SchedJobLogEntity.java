package com.izneus.bonfire.module.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sched_job_log")
public class SchedJobLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * sys_job表id
     */
    private String jobId;

    private String jobClass;

    private String jobMethod;

    private String param;

    /**
     * 任务执行状态
     */
    private String status;

    /**
     * 出错信息
     */
    private String message;

    /**
     * 执行消耗时间，单位毫秒
     */
    private Long durationMillis;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;


}
