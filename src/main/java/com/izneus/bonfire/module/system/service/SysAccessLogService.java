package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.ListAccessLogQuery;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;

/**
 * <p>
 * 系统_访问日志 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-08
 */
public interface SysAccessLogService extends IService<SysAccessLogEntity> {

    /**
     * 访问日志列表
     *
     * @param query 查询条件
     * @return page
     */
    Page<SysAccessLogEntity> listAccessLogs(ListAccessLogQuery query);

}
