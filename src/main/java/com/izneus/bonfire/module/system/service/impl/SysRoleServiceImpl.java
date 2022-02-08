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
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.entity.SysRolePrivilegeEntity;
import com.izneus.bonfire.module.system.mapper.SysRoleMapper;
import com.izneus.bonfire.module.system.service.SysRolePrivilegeService;
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

    private final SysRolePrivilegeService rolePrivService;

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
        List<SysRolePrivilegeEntity> privs = rolePrivService.list(
                new LambdaQueryWrapper<SysRolePrivilegeEntity>().eq(SysRolePrivilegeEntity::getId, roleId)
        );
        List<String> privIds = privs.stream().map(SysRolePrivilegeEntity::getPrivId).collect(Collectors.toList());
        roleVO.setPrivilegeIds(privIds);
        return roleVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRoleById(UpdateRoleQuery query) {
        // 更新角色表
        SysRoleEntity roleEntity = BeanUtil.copyProperties(query, SysRoleEntity.class);
        updateById(roleEntity);
        setRolePrivById(query.getId(), query.getPrivilegeIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleById(String id) {
        // 先删除角色表
        removeById(id);
        // 再删除角色对应的权限
        rolePrivService.remove(new LambdaQueryWrapper<SysRolePrivilegeEntity>()
                .eq(SysRolePrivilegeEntity::getRoleId, id));
    }

    @Override
    public void deleteRoleBatch(List<String> roleIds) {
        removeByIds(roleIds);
        // todo 潜在的in查询，可能超过1000
        rolePrivService.remove(new LambdaQueryWrapper<SysRolePrivilegeEntity>()
                .in(SysRolePrivilegeEntity::getRoleId, roleIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRolePrivById(String roleId, List<String> privIds) {
        if (roleId == null) {
            return;
        }
        // 先删除角色当前权限
        rolePrivService.remove(new LambdaQueryWrapper<SysRolePrivilegeEntity>()
                .eq(SysRolePrivilegeEntity::getRoleId, roleId));
        // 插入权限
        if (privIds != null && privIds.size() > 0) {
            List<SysRolePrivilegeEntity> rolePrivs = privIds.stream().map(privId -> {
                SysRolePrivilegeEntity rolePrivilegeEntity = new SysRolePrivilegeEntity();
                rolePrivilegeEntity.setRoleId(roleId);
                rolePrivilegeEntity.setPrivId(privId);
                return rolePrivilegeEntity;
            }).collect(Collectors.toList());
            rolePrivService.saveBatch(rolePrivs);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createRole(RoleQuery roleQuery) {
        // 新增角色
        SysRoleEntity roleEntity = BeanUtil.copyProperties(roleQuery, SysRoleEntity.class);
        String roleId = save(roleEntity) ? roleEntity.getId() : null;
        // 新增角色权限关联
        setRolePrivById(roleId, roleQuery.getPrivilegeIds());
        return roleId;
    }
}
