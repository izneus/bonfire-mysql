package com.izneus.bonfire.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.config.BonfireProperties;
import com.izneus.bonfire.module.system.service.dto.GetUserDTO;
import com.izneus.bonfire.module.system.service.dto.UserDTO;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.entity.SysUserRoleEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import com.izneus.bonfire.module.system.service.SysUserRoleService;
import com.izneus.bonfire.module.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统_用户 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-06-28
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    private final BonfireProperties bonfireProperties;
    private final SysUserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createUser(UserDTO userDTO) {
        // 新增用户
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(bonfireProperties.getDefaultPassword()));
        String userId = save(userEntity) ? userEntity.getId() : null;
        // 新增用户角色关联
        saveUserRoles(userId, userDTO.getRoleIds());
        return userId;
    }

    @Override
    public GetUserDTO getUserById(String userId) {
        // 查询用户表
        SysUserEntity userEntity = getById(userId);
        if (userEntity == null) {
            return null;
        }
        GetUserDTO userDTO = new GetUserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        // 查询用户角色
        List<SysUserRoleEntity> userRoles = userRoleService.list(
                new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId, userId)
        );
        List<String> roleIds = new ArrayList<>();
        for (SysUserRoleEntity userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        userDTO.setRoleIds(roleIds);
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserById(String userId, UserDTO userDTO) {
        // 更新用户表
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(userDTO, userEntity);
        userEntity.setId(userId);
        updateById(userEntity);
        // 更新用户角色表，先删除现有角色信息，再重新插入新角色
        userRoleService.remove(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId, userId));
        saveUserRoles(userId, userDTO.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserById(String userId) {
        // 删除用户表
        removeById(userId);
        // 删除用户的角色
        userRoleService.remove(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId, userId));
    }

    private void saveUserRoles(String userId, List<String> roleIds) {
        if (userId == null) {
            return;
        }
        if (roleIds != null && roleIds.size() > 0) {
            List<SysUserRoleEntity> userRoles = new ArrayList<>();
            for (String roleId : roleIds) {
                SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                userRoleEntity.setUserId(userId);
                userRoleEntity.setRoleId(roleId);
                userRoles.add(userRoleEntity);
            }
            userRoleService.saveBatch(userRoles);
        }
    }

}
