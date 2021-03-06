package com.izneus.bonfire.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.dto.ListAuthDTO;
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
    List<ListAuthDTO> listAuthsByUserId(@Param("userId") String userId);
}
