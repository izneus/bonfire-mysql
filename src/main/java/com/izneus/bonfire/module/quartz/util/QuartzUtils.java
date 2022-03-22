package com.izneus.bonfire.module.quartz.util;

import com.izneus.bonfire.common.constant.Constant;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import org.quartz.*;

/**
 * @author Izneus
 * @date 2020/10/26
 */
public class QuartzUtils {

    private final static String JOB_PREFIX = "JOB_";

    /**
     * 创建任务
     */
    public static void createJob(Scheduler scheduler, SchedJobEntity job) {
        // 下面是quartz的几个核心概念
        // JobDetail
        JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                .withIdentity(JOB_PREFIX + job.getId())
                .build();
        // 添加参数,在反射执行任务时候会用到
        jobDetail.getJobDataMap().put(Constant.JOB_KEY, job);

        // Trigger,通过cron表达式创建trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(JOB_PREFIX + job.getId())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                .build();

        // todo 开始调度前其他检测

        try {
            // Scheduler,执行任务
            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (job.getStatus().equals(Dict.JobStatus.PAUSE.getValue())) {
                pauseJob(scheduler, job.getId());
            }
        } catch (SchedulerException e) {
            throw new BadRequestException("创建调度任务失败", e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.pauseJob(new JobKey(JOB_PREFIX + jobId));
        } catch (SchedulerException e) {
            throw new BadRequestException("暂停调度任务失败", e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.resumeJob(new JobKey(JOB_PREFIX + jobId));
        } catch (SchedulerException e) {
            throw new BadRequestException("恢复调度任务失败", e);
        }
    }

    public static void deleteJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.deleteJob(new JobKey(JOB_PREFIX + jobId));
        } catch (SchedulerException e) {
            throw new BadRequestException("删除调度任务失败", e);
        }
    }

    public static void updateJob(Scheduler scheduler, SchedJobEntity job) {
        TriggerKey triggerKey = new TriggerKey(JOB_PREFIX + job.getId());
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            trigger.getJobDataMap().put(Constant.JOB_KEY, job);

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (job.getStatus().equals(Dict.JobStatus.PAUSE.getValue())) {
                pauseJob(scheduler, job.getId());
            }
        } catch (SchedulerException e) {
            throw new BadRequestException("更新定时任务失败", e);
        }
    }

    public static void runOnce(Scheduler scheduler, String jobId) {
        try {
            scheduler.triggerJob(new JobKey(JOB_PREFIX + jobId));
        } catch (SchedulerException e) {
            throw new BadRequestException("立即执行一次任务失败", e);
        }
    }

}
