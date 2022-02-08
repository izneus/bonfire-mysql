package com.izneus.bonfire.module.quartz.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.quartz.controller.v1.query.ListLogQuery;
import com.izneus.bonfire.module.quartz.controller.v1.vo.ListLogVO;
import com.izneus.bonfire.module.quartz.entity.SchedJobLogEntity;
import com.izneus.bonfire.module.quartz.service.SchedJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 调度任务日志表 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Api(tags = "系统:任务执行日志")
@RestController
@RequestMapping("/api/v1/jobLog")
@RequiredArgsConstructor
public class SchedJobLogController {

    private final SchedJobLogService jobLogService;

    @AccessLog("任务执行日志列表")
    @ApiOperation("任务执行日志列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sched:log:list') or hasAuthority('admin')")
    public BasePageVO<ListLogVO> listLogs(@Validated @RequestBody ListLogQuery query) {
        Page<SchedJobLogEntity> page = jobLogService.listLogsByJobId(query);
        // 查询结果转换成vo
        List<ListLogVO> rows = page.getRecords().stream()
                .map(log -> BeanUtil.copyProperties(log, ListLogVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

}
