package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.ListRoleQuery;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;

import java.util.List;

/**
 * <p>
 * 系统_角色 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
public interface SysRoleService extends IService<SysRoleEntity> {
    /**
     * 查询角色列表
     *
     * @param query 查询条件
     * @return page
     */
    Page<SysRoleEntity> listRoles(ListRoleQuery query);

    /**
     * 删除角色
     *
     * @param id roleId
     */
    void deleteRoleById(String id);

    /**
     * 给角色设置权限
     *
     * @param roleId  roleId
     * @param authIds authId列表
     */
    void setRoleAuthById(String roleId, List<String> authIds);
}
