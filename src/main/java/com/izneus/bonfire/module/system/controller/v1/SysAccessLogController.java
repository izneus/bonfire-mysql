package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.IdsQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListAccessLogQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.ListAccessLogVO;
import com.izneus.bonfire.module.system.entity.SysAccessLogEntity;
import com.izneus.bonfire.module.system.service.SysAccessLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @AccessLog("访问日志详情")
    @ApiOperation("访问日志详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:accessLog:get') or hasAuthority('admin')")
    public SysAccessLogEntity getAccessLog(@Validated @RequestBody IdQuery query) {
        return logService.getById(query.getId());
    }

    @AccessLog("删除访问日志")
    @ApiOperation("删除访问日志")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:accessLog:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Validated @RequestBody IdQuery query) {
        logService.removeById(query.getId());
    }

    @AccessLog("批量删除访问日志")
    @ApiOperation("批量删除访问日志")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:accessLog:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@Validated @RequestBody IdsQuery query) {
        logService.removeByIds(query.getIds());
    }


}
