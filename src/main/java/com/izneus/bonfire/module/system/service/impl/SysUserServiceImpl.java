package com.izneus.bonfire.module.system.service.impl;

import com.izneus.bonfire.config.BonfireProperties;
import com.izneus.bonfire.module.system.controller.v1.query.CreateUserQuery;
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
    public String createUser(CreateUserQuery createUserQuery) {
        // 新增用户
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(createUserQuery, userEntity);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(bonfireProperties.getDefaultPassword()));
        String userId = save(userEntity) ? userEntity.getId() : null;
        // 新增用户角色关联
        List<String> roleIds = createUserQuery.getRoleIds();
        if (roleIds != null && roleIds.size() > 0) {
            List<SysUserRoleEntity> userRoles = new ArrayList<>();
            for (String roleId: roleIds) {
                SysUserRoleEntity userRoleEntity = new SysUserRoleEntity();
                userRoleEntity.setUserId(userId);
                userRoleEntity.setRoleId(roleId);
                userRoles.add(userRoleEntity);
            }
            userRoleService.saveBatch(userRoles);
        }
        return userId;
    }

}
