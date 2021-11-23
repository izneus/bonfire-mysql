package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.query.DictQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListDictQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.CacheDictVO;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.mapper.SysDictMapper;
import com.izneus.bonfire.module.system.service.SysDictService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public Page<SysDictEntity> listDicts(ListDictQuery query) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysDictEntity>()
                        .like(StringUtils.hasText(query.getDictType()),
                                SysDictEntity::getDictType, query.getDictType())
                        .like(StringUtils.hasText(query.getDictLabel()),
                                SysDictEntity::getDictLabel, query.getDictLabel())
                        .like(StringUtils.hasText(query.getStatus()),
                                SysDictEntity::getStatus, query.getStatus())
                        .orderByDesc(SysDictEntity::getCreateTime)
        );
    }

    @Override
    @CacheEvict(key = "'all'")
    public String createDict(DictQuery dictQuery) {
        SysDictEntity dictEntity = BeanUtil.copyProperties(dictQuery, SysDictEntity.class);
        save(dictEntity);
        return dictEntity.getId();
    }

    @Override
    @CacheEvict(key = "'all'")
    public void updateDictById(String dictId, DictQuery query) {
        SysDictEntity dictEntity = BeanUtil.copyProperties(query, SysDictEntity.class);
        dictEntity.setId(dictId);
        updateById(dictEntity);
    }

    @Override
    @CacheEvict(key = "'all'")
    public void deleteDictById(String dictId) {
        removeById(dictId);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<CacheDictVO> cacheDicts() {
        // 这里很简单的缓存了所有字典，增删改字典的时候直接删除了缓存
        List<SysDictEntity> dicts = list();
        return dicts.stream().map(dict -> BeanUtil.copyProperties(dict, CacheDictVO.class))
                .collect(Collectors.toList());
    }
}
