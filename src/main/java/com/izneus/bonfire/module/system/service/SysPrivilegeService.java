package com.izneus.bonfire.module.system.service;

import com.izneus.bonfire.module.system.controller.v1.vo.PrivTreeVO;
import com.izneus.bonfire.module.system.entity.SysPrivilegeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统_权限 服务类
 * </p>
 *
 * @author Izneus
 * @since 2022-01-19
 */
public interface SysPrivilegeService extends IService<SysPrivilegeEntity> {

    /**
     * 查询权限树
     *
     * @return 权限树
     */
    List<PrivTreeVO> getPrivilegeTree();

}
