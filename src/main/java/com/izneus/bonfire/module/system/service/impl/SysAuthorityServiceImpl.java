package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.query.ListAuthQuery;
import com.izneus.bonfire.module.system.entity.SysAuthorityEntity;
import com.izneus.bonfire.module.system.mapper.SysAuthorityMapper;
import com.izneus.bonfire.module.system.service.SysAuthorityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统_权限 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
@Service
public class SysAuthorityServiceImpl extends ServiceImpl<SysAuthorityMapper, SysAuthorityEntity>
        implements SysAuthorityService {

    @Override
    public Page<SysAuthorityEntity> listAuthorities(ListAuthQuery query) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysAuthorityEntity>()
                        .eq(StrUtil.isNotBlank(query.getType()), SysAuthorityEntity::getType, query.getType())
                        .and(lambdaQueryWrapper -> lambdaQueryWrapper
                                .like(StrUtil.isNotBlank(query.getQuery()),
                                        SysAuthorityEntity::getAuthority, query.getQuery())
                                .or()
                                .like(StrUtil.isNotBlank(query.getQuery()),
                                        SysAuthorityEntity::getRemark, query.getQuery()))

        );
    }
}
