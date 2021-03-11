package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.DictQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListDictQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.CacheDictVO;
import com.izneus.bonfire.module.system.entity.SysDictEntity;

import java.util.List;

/**
 * <p>
 * 系统_字典 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-09-08
 */
public interface SysDictService extends IService<SysDictEntity> {
    /**
     * 字典列表
     *
     * @param query 查询条件
     * @return page
     */
    Page<SysDictEntity> listDicts(ListDictQuery query);

    /**
     * 创建字典
     *
     * @param dictQuery 参数
     * @return dictId
     */
    String createDict(DictQuery dictQuery);

    /**
     * 更新字典
     *
     * @param dictId id
     * @param query  参数
     */
    void updateDictById(String dictId, DictQuery query);

    /**
     * 删除字典
     *
     * @param dictId id
     */
    void deleteDictById(String dictId);

    /**
     * 缓存所有字典
     *
     * @return 字典列表
     */
    List<CacheDictVO> cacheDicts();

}
