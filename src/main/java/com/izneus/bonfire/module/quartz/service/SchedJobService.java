package com.izneus.bonfire.module.quartz.service;

import com.izneus.bonfire.module.quartz.controller.v1.query.JobQuery;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 调度任务表 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
public interface SchedJobService extends IService<SchedJobEntity> {

    String createJob(JobQuery query);

    void batchPauseJobs(List<String> ids);

    void batchResumeJobs(List<String> ids);

    void batchDeleteJobs(List<String> ids);

    void deleteJob(String id);

    void updateJob(String id, JobQuery query);

}
