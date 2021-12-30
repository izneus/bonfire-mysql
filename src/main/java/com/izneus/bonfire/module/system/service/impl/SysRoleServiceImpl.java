package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.query.ListRoleQuery;
import com.izneus.bonfire.module.system.controller.v1.query.RoleQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdateRoleQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.RoleVO;
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
    public RoleVO getRoleById(String roleId) {
        // 查询角色表
        SysRoleEntity roleEntity = getById(roleId);
        if (roleEntity == null) {
            return null;
        }
        RoleVO roleVO = BeanUtil.copyProperties(roleEntity, RoleVO.class);
        // 查询角色权限
        List<SysRoleAuthorityEntity> roles = roleAuthorityService.list(
                new LambdaQueryWrapper<SysRoleAuthorityEntity>().eq(SysRoleAuthorityEntity::getRoleId, roleId)
        );
        List<String> authorityIds = roles.stream().map(SysRoleAuthorityEntity::getAuthorityId).collect(Collectors.toList());
        roleVO.setAuthorityIds(authorityIds);
        return roleVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleById(UpdateRoleQuery query) {
        // 更新角色表
        SysRoleEntity roleEntity = BeanUtil.copyProperties(query, SysRoleEntity.class);
        updateById(roleEntity);
        setRoleAuthById(query.getId(), query.getAuthorityIds());
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
    public void deleteRoleBatch(List<String> roleIds) {
        removeByIds(roleIds);
        // todo 潜在的in查询，可能超过1000
        roleAuthorityService.remove(new LambdaQueryWrapper<SysRoleAuthorityEntity>().in(SysRoleAuthorityEntity::getRoleId, roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRoleAuthById(String roleId, List<String> authIds) {
        if (roleId == null) {
            return;
        }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createRole(RoleQuery roleQuery) {
        // 新增角色
        SysRoleEntity roleEntity = BeanUtil.copyProperties(roleQuery, SysRoleEntity.class);
        String roleId = save(roleEntity) ? roleEntity.getId() : null;
        // 新增角色权限关联
        setRoleAuthById(roleId, roleQuery.getAuthorityIds());
        return roleId;
    }
}
