package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;

import java.util.List;

/**
 * <p>
 * 系统_角色 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
public interface SysRoleService extends IService<SysRoleEntity> {
    void deleteRoleById(String id);

    void setRoleAuthById(String roleId, List<String> authIds);
}
