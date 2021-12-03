package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.ListAuthQuery;
import com.izneus.bonfire.module.system.entity.SysAuthorityEntity;

import java.util.List;

/**
 * <p>
 * 系统_权限 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
public interface SysAuthorityService extends IService<SysAuthorityEntity> {
    /**
     * 查询权限列表
     *
     * @param query 查询条件
     * @return 分页信息
     */
    Page<SysAuthorityEntity> listAuthorities(ListAuthQuery query);

    /**
     * 批量删除权限
     *
     * @param ids id列表
     */
    void deleteAuthoritiesByIds(List<String> ids);
}
