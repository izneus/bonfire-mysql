package com.izneus.bonfire.module.quartz.service.impl;

import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.izneus.bonfire.module.quartz.mapper.SchedJobLogMapper;
import com.izneus.bonfire.module.quartz.service.SchedJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
