package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.CreateUserQuery;
import com.izneus.bonfire.module.system.entity.SysUserEntity;

/**
 * <p>
 * 系统_用户 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-06-28
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 新增用户
     *
     * @param createUserQuery 用户
     * @return 新建成功返回用户id，失败返回null
     */
    String createUser(CreateUserQuery createUserQuery);

}
