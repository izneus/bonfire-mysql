package com.izneus.bonfire.module.system.service.impl;

import com.izneus.bonfire.config.BonfireProperties;
import com.izneus.bonfire.module.system.controller.v1.query.CreateUserQuery;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import com.izneus.bonfire.module.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Override
    public String createUser(CreateUserQuery createUserQuery) {
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(createUserQuery, userEntity);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(bonfireProperties.getDefaultPassword()));
        return save(userEntity) ? userEntity.getId() : null;
    }

}
