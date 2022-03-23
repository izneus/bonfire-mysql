package com.izneus.bonfire.module.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.util.CommonUtil;
import com.izneus.bonfire.module.quartz.controller.v1.query.ListLogQuery;
import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.izneus.bonfire.module.quartz.mapper.SchedJobLogMapper;
import com.izneus.bonfire.module.quartz.service.SchedJobLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 调度任务日志表 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Service
public class SchedJobLogServiceImpl extends ServiceImpl<SchedJobLogMapper, SchedJobLogEntity> implements SchedJobLogService {

    @Override
    public Page<SchedJobLogEntity> listLogsByJobId(ListLogQuery query) {
        // 解析设置时间
        List<Date> createTimes = CommonUtil.parseDateRange(query.getCreateTime());
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SchedJobLogEntity>()
                        .ge(createTimes.get(0) != null, SchedJobLogEntity::getCreateTime, createTimes.get(0))
                        .le(createTimes.get(1) != null, SchedJobLogEntity::getCreateTime, createTimes.get(1))
                        .orderByDesc(SchedJobLogEntity::getCreateTime)
                        .orderByDesc(SchedJobLogEntity::getId)
        );
    }
}
