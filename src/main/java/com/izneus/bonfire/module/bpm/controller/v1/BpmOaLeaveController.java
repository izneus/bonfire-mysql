package com.izneus.bonfire.module.bpm.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.bpm.controller.v1.query.LeaveQuery;
import com.izneus.bonfire.module.bpm.service.BpmOaLeaveService;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 请假业务数据
 * </p>
 *
 * @author Izneus
 * @since 2022-06-14
 */
@Api(tags = "工作流:OA审批")
@RestController
@RequestMapping("/api/v1/bpm/oa")
@RequiredArgsConstructor
public class BpmOaLeaveController {

    private final BpmOaLeaveService oaLeaveService;

    @AccessLog("新增请假")
    @ApiOperation("新增请假")
    @PostMapping("/createLeave")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createLeave(@Validated @RequestBody LeaveQuery leaveQuery) {
        String id = oaLeaveService.createLeave(leaveQuery);
        return new IdVO(id);
    }

}
