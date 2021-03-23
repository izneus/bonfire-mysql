package com.izneus.bonfire.module.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.system.entity.SysNoticeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统通知表 Mapper 接口
 * </p>
 *
 * @author Izneus
 * @since 2020-12-10
 */
public interface SysNoticeMapper extends BaseMapper<SysNoticeEntity> {

    List<SysNoticeEntity> listNewGlobalNoticesByUserId(@Param("userId") String userId);

    Page<SysNoticeEntity> listNoticesByUserId(@Param("page") Page page,
                                              @Param("userId") String userId, @Param("status") String status);

}
