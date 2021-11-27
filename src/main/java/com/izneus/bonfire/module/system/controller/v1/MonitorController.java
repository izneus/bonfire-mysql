package com.izneus.bonfire.module.system.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.vo.MonitorVO;
import com.izneus.bonfire.module.system.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Izneus
 * @date 2020/09/24
 */
@Api(tags = "系统:硬件监控")
@RestController
@RequestMapping("/api/v1/monitor")
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorService monitorService;

    @AccessLog("系统详情")
    @ApiOperation("系统详情")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:monitor:list') or hasAuthority('admin')")
    public MonitorVO listMonitors() {
        return monitorService.listMonitors();
    }
}
