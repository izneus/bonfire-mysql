package com.izneus.bonfire.module.bpm.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListProcessInstanceQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ListProcessInstanceVO;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ProcessInstanceVO;
import com.izneus.bonfire.module.bpm.service.BpmProcessInstanceService;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程实例
 *
 * @author Izneus
 * @since 2022-06-01
 */
@Api(tags = "工作流:流程实例")
@RequestMapping("/api/v1/bpm/processInstance")
@RequiredArgsConstructor
@RestController
public class BpmProcessInstanceController {

    private final BpmProcessInstanceService processInstanceService;

    @AccessLog("实例列表")
    @ApiOperation("实例列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('bpm:processInstance:list') or hasAuthority('admin')")
    public BasePageVO<ListProcessInstanceVO> listProcessInstances(@Validated @RequestBody ListProcessInstanceQuery query) {
        return processInstanceService.listProcessInstances(query);
    }

    @AccessLog("实例详情")
    @ApiOperation("实例详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:processInstance:get') or hasAuthority('admin')")
    public ProcessInstanceVO getProcessInstance(@Validated @RequestBody IdQuery query) {
        return processInstanceService.getProcessInstance(query.getId());
    }

    /*@AccessLog("历史活动列表")
    @ApiOperation("历史活动列表")
    @PostMapping("/listActivities")
    @PreAuthorize("hasAuthority('sys:processInstance:listActivities') or hasAuthority('admin')")
    public void listActivities(@Validated @RequestBody IdQuery query) {
        List<HistoricActivityInstance> l = processInstanceService.listActivitiesByProcessInstanceId(query.getId());
        return;
    }*/

}
