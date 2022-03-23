package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.util.CommonUtil;
import com.izneus.bonfire.module.system.controller.v1.query.ListAccessLogQuery;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.izneus.bonfire.module.system.mapper.SysAccessLogMapper;
import com.izneus.bonfire.module.system.service.SysAccessLogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        // 解析设置时间
        List<Date> createTimes = CommonUtil.parseDateRange(query.getCreateTime());
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysAccessLogEntity>()
                        .ge(createTimes.get(0) != null, SysAccessLogEntity::getCreateTime, createTimes.get(0))
                        .le(createTimes.get(1) != null, SysAccessLogEntity::getCreateTime, createTimes.get(1))
                        .like(StrUtil.isNotBlank(query.getQuery()), SysAccessLogEntity::getDescription, query.getQuery())
                        .orderByDesc(SysAccessLogEntity::getCreateTime)
                        .orderByDesc(SysAccessLogEntity::getId)
        );

    }
}
