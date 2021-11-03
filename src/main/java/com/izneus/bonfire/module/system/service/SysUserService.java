package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdateUserQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.UserVO;
import com.izneus.bonfire.module.system.controller.v1.query.UserQuery;
import com.izneus.bonfire.module.system.entity.SysUserEntity;

import java.util.List;

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
     * 用户列表
     *
     * @param query ListUserQuery
     * @return Page<SysUserEntity>
     */
    Page<SysUserEntity> listUsers(ListUserQuery query);

    /**
     * 新增用户
     *
     * @param userQuery 用户
     * @return 新建成功返回用户id，失败返回null
     */
    String createUser(UserQuery userQuery);

    /**
     * 通过id获取用户详情
     *
     * @param userId userId
     * @return UserVO
     */
    UserVO getUserById(String userId);

    /**
     * 通过id更新用户信息
     *
     * @param query 用户信息
     */
    void updateUserById(UpdateUserQuery query);

    /**
     * 通过id删除用户信息
     *
     * @param userId userId
     */
    void removeUserById(String userId);

    void deleteUserBatch(List<String> userIds);

    /**
     * 导出用户，根据查询条件生成临时文件，返回文件名
     *
     * @param query 查询条件
     * @return 临时文件名
     */
    String exportUsers(ListUserQuery query);

    /**
     * 重置密码为默认密码
     *
     * @param userIds id列表
     * @return boolean
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean resetPasswordBatch(List<String> userIds);

    /**
     * 导入用户，预上传文件获得文件id
     *
     * @param fileId 文件id
     */
    void importUsers(String fileId);

    /**
     * 删除redis的密码重试k-v来解除登陆时候的密码重试过多的账号锁定
     *
     * @param username 用户名
     */
    void unlockUser(String username);

    /**
     * 主动将用户踢下线
     *
     * @param userId 用户id
     */
    void kickOut(String userId);
}
