package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.entity.SysDictEntity;

/**
 * <p>
 * 系统_字典 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-09-08
 */
public interface SysDictService extends IService<SysDictEntity> {

    String cacheDicts();

}
