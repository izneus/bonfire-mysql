package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.query.ListRoleQuery;
import com.izneus.bonfire.module.system.entity.SysRoleAuthorityEntity;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.mapper.SysRoleMapper;
import com.izneus.bonfire.module.system.service.SysRoleAuthorityService;
import com.izneus.bonfire.module.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统_角色 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    private final SysRoleAuthorityService roleAuthorityService;

    @Override
    public Page<SysRoleEntity> listRoles(ListRoleQuery query) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysRoleEntity>()
                        .like(StrUtil.isNotBlank(query.getQuery()), SysRoleEntity::getRoleName, query.getQuery())
                        .or()
                        .like(StrUtil.isNotBlank(query.getQuery()), SysRoleEntity::getRemark, query.getQuery())
                        .orderByDesc(SysRoleEntity::getCreateTime)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(String id) {
        // 先删除角色表
        removeById(id);
        // 再删除角色对应的权限
        roleAuthorityService.remove(new LambdaQueryWrapper<SysRoleAuthorityEntity>()
                .eq(SysRoleAuthorityEntity::getRoleId, id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRoleAuthById(String roleId, List<String> authIds) {
        // 先删除角色当前权限
        roleAuthorityService.remove(new LambdaQueryWrapper<SysRoleAuthorityEntity>()
                .eq(SysRoleAuthorityEntity::getRoleId, roleId));
        // 插入权限
        if (authIds != null && authIds.size() > 0) {
            List<SysRoleAuthorityEntity> roleAuths = authIds.stream().map(authId -> {
                SysRoleAuthorityEntity roleAuthorityEntity = new SysRoleAuthorityEntity();
                roleAuthorityEntity.setRoleId(roleId);
                roleAuthorityEntity.setAuthorityId(authId);
                return roleAuthorityEntity;
            }).collect(Collectors.toList());
            roleAuthorityService.saveBatch(roleAuths);
        }
    }
}
