package com.izneus.bonfire.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.query.ListAccessLogQuery;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;
import com.izneus.bonfire.module.system.mapper.SysAccessLogMapper;
import com.izneus.bonfire.module.system.service.SysAccessLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统_访问日志 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-08
 */
@Service
public class SysAccessLogServiceImpl extends ServiceImpl<SysAccessLogMapper, SysAccessLogEntity>
        implements SysAccessLogService {

    @Override
    public Page<SysAccessLogEntity> listAccessLogs(ListAccessLogQuery query) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysAccessLogEntity>()
                        .ge(SysAccessLogEntity::getCreateTime, query.getStartTime())
                        .le(SysAccessLogEntity::getCreateTime, query.getEndTime())
                        .eq(SysAccessLogEntity::getClientIp, query.getClientIp())
                        .orderByDesc(SysAccessLogEntity::getCreateTime)
        );

    }
}
