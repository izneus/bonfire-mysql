package com.izneus.bonfire.module.quartz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.module.quartz.controller.v1.query.JobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.query.ListJobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.query.UpdateJobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.vo.JobVO;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import com.izneus.bonfire.module.quartz.mapper.SchedJobMapper;
import com.izneus.bonfire.module.quartz.service.SchedJobService;
import com.izneus.bonfire.module.quartz.util.QuartzUtils;
import com.izneus.bonfire.module.system.controller.v1.vo.PrivilegeVO;
import com.izneus.bonfire.module.system.entity.SysPrivilegeEntity;
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
    public Page<SchedJobEntity> listJobs(ListJobQuery query) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SchedJobEntity>()
                        .like(StrUtil.isNotBlank(query.getJobName()), SchedJobEntity::getJobName, query.getJobName())
                        .eq(StrUtil.isNotBlank(query.getStatus()), SchedJobEntity::getStatus, query.getStatus())
                        .orderByDesc(SchedJobEntity::getCreateTime)
        );
    }

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
    public void updateJob(UpdateJobQuery query) {
        SchedJobEntity jobEntity = BeanUtil.copyProperties(query, SchedJobEntity.class);
        jobEntity.setId(query.getId());
        QuartzUtils.updateJob(scheduler, jobEntity);
        updateById(jobEntity);
    }

    @Override
    public JobVO getJobById(String jobId) {
        // 查询任务表
        SchedJobEntity schedJobEntity = getById(jobId);
        if (schedJobEntity == null) {
            return null;
        }
        JobVO jobVO = BeanUtil.copyProperties(schedJobEntity, JobVO.class);
        return jobVO;
    }
}
