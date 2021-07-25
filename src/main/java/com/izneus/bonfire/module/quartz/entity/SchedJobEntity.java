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
 * 调度任务表
 * </p>
 *
 * @author Izneus
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sched_job")
public class SchedJobEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid,pk
     */
    private String id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组
     */
    private String jobGroup;

    /**
     * 任务具体实现的方法名
     */
    private String jobMethod;

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

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 任务状态
     */
    private String status;

    /**
     * 任务具体实现的类名
     */
    private String jobClass;

    /**
     * 调用任务参数
     */
    private String param;


}
