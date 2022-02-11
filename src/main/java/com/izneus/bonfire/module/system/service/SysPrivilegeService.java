package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.system.controller.v1.query.ListPrivilegeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.PrivilegeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdatePrivilegeQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.PrivilegeVO;
import com.izneus.bonfire.module.system.service.dto.PrivTreeDTO;
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
    List<PrivTreeDTO> getPrivilegeTree();

    /**
     * 查询权限列表
     *
     * @return 权限列表
     */
    Page<SysPrivilegeEntity> getPrivilegeList(ListPrivilegeQuery query);

    /**
     * 新增权限
     *
     * @return 权限ID
     */
    String createPrivilege(PrivilegeQuery privilegeQuery);

    /**
     * 权限详情
     *
     * @return 权限详情
     */
    PrivilegeVO getPrivilegeById(String id);

    /**
     * 修改权限
     */
    void updatePrivilegeById( UpdatePrivilegeQuery query);

    /**
     * 删除权限
     */
    void removePrivilegeById(String id);

    /**
     * 批量删除权限
     */
    void deletePrivilegeBatch(List<String> ids);

}
