package com.izneus.bonfire.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.mapper.SysDictMapper;
import com.izneus.bonfire.module.system.service.SysDictService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统_字典 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-09-08
 */
@Service
@CacheConfig(cacheNames = "dict")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictEntity> implements SysDictService {

    @Override
    @Cacheable(key = "'all'")
    public String cacheDicts() {
        List<SysDictEntity> dictEntities = list();



        return "999xxx";
    }
}
