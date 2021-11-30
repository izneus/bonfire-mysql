package com.izneus.bonfire.module.quartz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.quartz.controller.v1.query.ListLogQuery;
import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 调度任务日志表 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
public interface SchedJobLogService extends IService<SchedJobLogEntity> {
    Page<SchedJobLogEntity> listLogsByJobId(ListLogQuery query);

}
