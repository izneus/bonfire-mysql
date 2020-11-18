package com.izneus.bonfire.module.quartz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.module.quartz.controller.v1.query.JobQuery;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import com.izneus.bonfire.module.quartz.mapper.SchedJobMapper;
import com.izneus.bonfire.module.quartz.service.SchedJobService;
import com.izneus.bonfire.module.quartz.util.QuartzUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 调度任务表 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SchedJobServiceImpl extends ServiceImpl<SchedJobMapper, SchedJobEntity> implements SchedJobService {

    private final Scheduler scheduler;

    @Override
    public String createJob(JobQuery query) {
        SchedJobEntity job = BeanUtil.copyProperties(query, SchedJobEntity.class);
        // 写sched_job表，保存当前任务
        save(job);
        // quartz调度
        QuartzUtils.createJob(scheduler, job);
        return job.getId();
    }

    @Override
    public void batchPauseJobs(List<String> ids) {
        // 空ids直接返回
        if (ids == null || ids.size() < 1) {
            return;
        }
        for (String id : ids) {
            QuartzUtils.pauseJob(scheduler, id);
        }
        // 更新sched_job表的任务状态为暂停
        update(new LambdaUpdateWrapper<SchedJobEntity>().in(SchedJobEntity::getId, ids)
                .set(SchedJobEntity::getStatus, Dict.JobStatus.PAUSE.getValue()));
    }

    @Override
    public void batchResumeJobs(List<String> ids) {
        // 空ids直接返回
        if (ids == null || ids.size() < 1) {
            return;
        }
        for (String id : ids) {
            QuartzUtils.resumeJob(scheduler, id);
        }
        // 更新sched_job表的任务状态为正常
        update(new LambdaUpdateWrapper<SchedJobEntity>().in(SchedJobEntity::getId, ids)
                .set(SchedJobEntity::getStatus, Dict.JobStatus.OK.getValue()));

    }

    @Override
    public void batchDeleteJobs(List<String> ids) {
        // 空ids直接返回
        if (ids == null || ids.size() < 1) {
            return;
        }
        for (String id : ids) {
            QuartzUtils.deleteJob(scheduler, id);
        }
        // 删除sched_job表记录
        removeByIds(ids);
    }

    @Override
    public void deleteJob(String id) {
        QuartzUtils.deleteJob(scheduler, id);
        // 删除sched_job表记录
        removeById(id);
    }

    @Override
    public void updateJob(String id, JobQuery query) {
        SchedJobEntity jobEntity = BeanUtil.copyProperties(query, SchedJobEntity.class);
        jobEntity.setId(id);
        QuartzUtils.updateJob(scheduler, jobEntity);
        updateById(jobEntity);
    }
}
