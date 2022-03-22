package com.izneus.bonfire.module.quartz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.quartz.controller.v1.query.JobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.query.ListJobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.query.UpdateJobQuery;
import com.izneus.bonfire.module.quartz.controller.v1.vo.JobVO;
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

    /**
     * 调度任务列表
     *
     * @param query 查询条件
     * @return 分页调度任务信息
     */
    Page<SchedJobEntity> listJobs(ListJobQuery query);

    /**
     * 创建调度任务
     *
     * @param query 任务信息
     * @return 任务id
     */
    String createJob(JobQuery query);

    /**
     * 批量暂停任务
     *
     * @param ids 任务id列表
     */
    void batchPauseJobs(List<String> ids);

    /**
     * 批量恢复任务
     *
     * @param ids 任务id列表
     */
    void batchResumeJobs(List<String> ids);

    /**
     * 批量删除任务
     *
     * @param ids 任务id列表
     */
    void batchDeleteJobs(List<String> ids);

    /**
     * 删除任务
     *
     * @param id 任务id
     */
    void deleteJob(String id);

    /**
     * 更新任务
     *
     * @param query 任务信息
     */
    void updateJob(UpdateJobQuery query);

    /**
     * 更新任务
     *
     * @return  任务信息
     */
    JobVO getJobById(String id);

    void runJobOnce(String id);

}
