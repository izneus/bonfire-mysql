package com.izneus.bonfire.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.entity.SysRoleAuthorityEntity;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.mapper.SysRoleMapper;
import com.izneus.bonfire.module.system.service.SysRoleAuthorityService;
import com.izneus.bonfire.module.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
            List<SysRoleAuthorityEntity> roleAuths = new ArrayList<>();
            for (String authId : authIds) {
                SysRoleAuthorityEntity roleAuthorityEntity = new SysRoleAuthorityEntity();
                roleAuthorityEntity.setRoleId(roleId);
                roleAuthorityEntity.setAuthorityId(authId);
                roleAuths.add(roleAuthorityEntity);
            }
            roleAuthorityService.saveBatch(roleAuths);
        }
    }
}
