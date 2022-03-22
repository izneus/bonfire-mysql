package com.izneus.bonfire.module.quartz.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.quartz.controller.v1.query.JobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.query.ListJobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.query.UpdateJobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.vo.JobVO;
import com.izneus.bonfire.module.quartz.controller.v1.vo.LatestJobLogVO;
import com.izneus.bonfire.module.quartz.controller.v1.vo.ListJobVO;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.izneus.bonfire.module.quartz.mapper.SchedJobLogMapper;
import com.izneus.bonfire.module.quartz.service.SchedJobLogService;
import com.izneus.bonfire.module.quartz.service.SchedJobService;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.IdsQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 调度任务表 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Api(tags = "系统:调度任务")
@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
public class SchedJobController {

    private final SchedJobService jobService;
    private final SchedJobLogMapper jobLogMapper;
    private final SchedJobLogService jobLogService;

    @AccessLog("任务列表")
    @ApiOperation("任务列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sched:job:list') or hasAuthority('admin')")
    public BasePageVO<ListJobVO> listJobs(@Validated @RequestBody ListJobQuery query) {
        Page<SchedJobEntity> page = jobService.listJobs(query);
        // 查询上一次运行情况
        List<String> jobIds = page.getRecords().stream().map(SchedJobEntity::getId).collect(Collectors.toList());
        List<SchedJobLogEntity> jobLogs = jobLogMapper.listLastJobLog(jobIds);
        // 准备vo
        List<ListJobVO> rows = page.getRecords().stream()
                .map(job -> {
                    ListJobVO vo = BeanUtil.copyProperties(job, ListJobVO.class);
                    // 计算下一次执行时间
                    CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(vo.getCron());
                    Date now = new Date();
                    Date nextRunTime = cronSequenceGenerator.next(now);
                    vo.setNextRunTime(nextRunTime);
                    // 拼接上次执行时间和耗时
                    for (SchedJobLogEntity jobLog : jobLogs) {
                        if (jobLog.getJobId().equals(vo.getId())) {
                            vo.setPrevRunTime(jobLog.getCreateTime());
                            vo.setDurationMillis(jobLog.getDurationMillis());
                            break;
                        }
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增任务")
    @ApiOperation("新增任务")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sched:job:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createJob(@Validated @RequestBody JobQuery query) {
        String id = jobService.createJob(query);
        return new IdVO(id);
    }

    @AccessLog("任务详情")
    @ApiOperation("任务详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sched:job:get') or hasAuthority('admin')")
    public JobVO getJobById(@Validated @RequestBody IdQuery query) {
        return jobService.getJobById(query.getId());
    }

    @AccessLog("更新任务")
    @ApiOperation("更新任务")
    @PreAuthorize("hasAuthority('sched:job:update') or hasAuthority('admin')")
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateJob(@Validated @RequestBody UpdateJobQuery query) {
        jobService.updateJob(query);
    }

    @AccessLog("批量暂停任务")
    @ApiOperation("批量暂停任务")
    @PostMapping("/batchPause")
    @PreAuthorize("hasAuthority('sched:job:pause') or hasAuthority('admin')")
    public void pauseJobs(@Validated @RequestBody IdsQuery query) {
        jobService.batchPauseJobs(query.getIds());
    }

    @AccessLog("批量恢复任务")
    @ApiOperation("批量恢复任务")
    @PostMapping("/batchResume")
    @PreAuthorize("hasAuthority('sched:job:resume') or hasAuthority('admin')")
    public void resumeJobs(@Validated @RequestBody IdsQuery query) {
        jobService.batchResumeJobs(query.getIds());
    }

    @AccessLog("批量删除任务")
    @ApiOperation("批量删除任务")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sched:job:delete') or hasAuthority('admin')")
    public void deleteJobs(@Validated @RequestBody IdsQuery query) {
        jobService.batchDeleteJobs(query.getIds());
    }

    @AccessLog("删除任务")
    @ApiOperation("删除任务")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sched:job:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@Validated @RequestBody IdQuery query) {
        jobService.deleteJob(query.getId());
    }

    @AccessLog("立即执行一次")
    @ApiOperation("立即执行一次")
    @PostMapping("/runOnce")
    @PreAuthorize("hasAuthority('sched:job:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void runJobOnce(@Validated @RequestBody IdQuery query) {
        jobService.runJobOnce(query.getId());
    }

    @AccessLog("最后执行日志")
    @ApiOperation("最后执行日志")
    @PostMapping("/getLatestJobLog")
    @PreAuthorize("hasAuthority('sched:job:create') or hasAuthority('admin')")
    public LatestJobLogVO getLatestJobLog(@Validated @RequestBody IdQuery query) {
        SchedJobLogEntity jobLog = jobLogService.getOne(
                new LambdaQueryWrapper<SchedJobLogEntity>()
                        .eq(SchedJobLogEntity::getJobId, query.getId())
                        .orderByDesc(SchedJobLogEntity::getCreateTime)
                        .last("limit 1")
        );
        return BeanUtil.copyProperties(jobLog, LatestJobLogVO.class);
    }

}
