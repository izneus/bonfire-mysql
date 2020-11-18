package com.izneus.bonfire.module.quartz.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.izneus.bonfire.common.constant.Constant;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.izneus.bonfire.module.quartz.service.SchedJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

/**
 * 调度任务的通用类，通过反射获取具体的任务实现方法然后执行，这里通过一个拼接过的字符串来传递参数给具体的task方法，
 * 由具体实现方法自己通过分割符分割获得执行参数
 *
 * @author Izneus
 * @date 2020/10/27
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        // 获取参数
        SchedJobEntity job = (SchedJobEntity) jobExecutionContext.getMergedJobDataMap().get(Constant.JOB_KEY);
        // 获取调度任务日志服务的bean
        SchedJobLogService jobLogService = SpringUtil.getBean(SchedJobLogService.class);
        // 构建jobLog准备保存任务运行日志
        SchedJobLogEntity jobLog = BeanUtil.copyProperties(job, SchedJobLogEntity.class,
                "id", "status");
        jobLog.setJobId(job.getId());

        Instant startTime = Instant.now();

        try {
            Object target = SpringUtil.getBean(job.getJobClass());
            Method method = target.getClass().getDeclaredMethod(job.getJobMethod(), String.class);
            method.invoke(target, job.getParam());

            jobLog.setStatus(Constant.SUCCESS);
        } catch (Exception e) {
            /// e.printStackTrace();
            log.error("调度任务执行失败，job_id = " + job.getId(), e);

            jobLog.setStatus(Constant.FAIL);
            jobLog.setMessage(StrUtil.sub(e.toString(), 0, 2000));
        } finally {
            Instant endTime = Instant.now();
            long durationMillis = Duration.between(startTime, endTime).toMillis();
            jobLog.setDurationMillis(durationMillis);
            // 保存任务日志
            jobLogService.save(jobLog);
        }
    }


}
