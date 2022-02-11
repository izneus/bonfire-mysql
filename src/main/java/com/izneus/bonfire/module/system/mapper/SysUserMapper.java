package com.izneus.bonfire.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.dto.ListPrivDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统_用户 Mapper 接口
 * </p>
 *
 * @author Izneus
 * @since 2020-06-28
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {
    /**
     * 查询用户角色和权限
     *
     * @param userId userId
     * @return 用户的角色和权限
     */
    List<ListPrivDTO> listPrivsByUserId(@Param("userId") String userId);
}
