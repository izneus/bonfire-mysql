package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.service.dto.GetUserDTO;
import com.izneus.bonfire.module.system.service.dto.UserDTO;
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
     * @param userDTO 用户
     * @return 新建成功返回用户id，失败返回null
     */
    String createUser(UserDTO userDTO);

    /**
     * 通过id获取用户详情
     *
     * @param userId userId
     * @return GetUserDTO
     */
    GetUserDTO getUserById(String userId);

    /**
     * 通过id更新用户信息
     *
     * @param userId  userID
     * @param userDTO userDTO
     */
    void updateUserById(String userId, UserDTO userDTO);

    /**
     * 通过id删除用户信息
     *
     * @param userId userId
     */
    void removeUserById(String userId);

}
