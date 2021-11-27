package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.ListAccessLogQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.ListAccessLogVO;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;
import com.izneus.bonfire.module.system.service.SysAccessLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统_访问日志 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-08-08
 */
@Api(tags = "系统:访问日志")
@RequestMapping("/api/v1/accessLog")
@RestController
@RequiredArgsConstructor
public class SysAccessLogController {

    private final SysAccessLogService logService;

    @AccessLog("访问日志列表")
    @ApiOperation("访问日志列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:accessLog:list') or hasAuthority('admin')")
    public BasePageVO<ListAccessLogVO> listUsers(@Validated @RequestBody ListAccessLogQuery query) {
        Page<SysAccessLogEntity> page = logService.listAccessLogs(query);
        // 组装vo
        List<ListAccessLogVO> rows = page.getRecords().stream()
                .map(log -> BeanUtil.copyProperties(log, ListAccessLogVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

}
