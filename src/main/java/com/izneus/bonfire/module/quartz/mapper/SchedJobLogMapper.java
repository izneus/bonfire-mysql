package com.izneus.bonfire.module.quartz.mapper;

import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 调度任务日志表 Mapper 接口
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
public interface SchedJobLogMapper extends BaseMapper<SchedJobLogEntity> {

    List<SchedJobLogEntity> listLastJobLog(@Param("jobIds") List<String> jobIds);

}
