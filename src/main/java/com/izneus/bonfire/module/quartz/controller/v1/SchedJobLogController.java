package com.izneus.bonfire.module.quartz.controller.v1;


import com.izneus.bonfire.module.quartz.service.SchedJobLogService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 调度任务日志表 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-11-05
 */
@Api(tags = "系统:调度任务")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SchedJobLogController {

    private final SchedJobLogService jobLogService;

}
