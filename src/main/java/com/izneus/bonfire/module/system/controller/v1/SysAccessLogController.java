package com.izneus.bonfire.module.system.controller.v1;


import com.izneus.bonfire.common.annotation.AccessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统_访问日志 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-08-08
 */
@Api(tags = "访问日志")
@RequestMapping("/api/v1")
@RestController
public class SysAccessLogController {

//    @AccessLog("访问日志列表")
//    @ApiOperation("访问日志列表")
//    @GetMapping("/accessLogs")
//    @PreAuthorize("hasAuthority('sys:accessLogs:list')")
//    public ListAccessLogVO listUsers(ListAccessLogQuery listAccessLogQuery) {
//    }

}
