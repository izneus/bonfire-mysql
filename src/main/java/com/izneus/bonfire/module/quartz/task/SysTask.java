package com.izneus.bonfire.module.quartz.task;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;
import com.izneus.bonfire.module.system.service.SysAccessLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 调度任务的具体逻辑实现类，有新的任务，可以按需在该package下新建class实现
 *
 * @author Izneus
 * @date 2002/10/30
 */
@Component("sysTask")
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class SysTask {

    private final SysAccessLogService accessLogService;

    /**
     * 定时删除系统操作日志，普通的企业开发中，一般并不需要保留太久的操作日志备查
     */
    public void deleteAccessLog() {
        // 删除100天之前的操作日志
        String datetime = LocalDateTimeUtil.format(LocalDateTime.now().minusDays(100),
                DatePattern.NORM_DATETIME_PATTERN);
        accessLogService.remove(new LambdaQueryWrapper<SysAccessLogEntity>()
                .lt(SysAccessLogEntity::getCreateTime, datetime));
    }

    /**
     * 测试调度任务用的方法
     *
     * @param params 执行参数，这里采用的全部参数采用一条字符串传入，半角逗号分割，
     *               方法体内自己分割之后转换类型之后使用
     */
    public void test(String params) {
        log.info("SysTask.test执行，参数：{}", params);
    }
}
