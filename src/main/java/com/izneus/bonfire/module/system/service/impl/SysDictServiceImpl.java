package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.vo.CacheDictVO;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.mapper.SysDictMapper;
import com.izneus.bonfire.module.system.service.SysDictService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CacheDictVO> cacheDicts() {
        List<SysDictEntity> dicts = list();
        return dicts.stream().map(dict -> BeanUtil.copyProperties(dict, CacheDictVO.class))
                .collect(Collectors.toList());
    }
}
