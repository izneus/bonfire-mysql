package com.izneus.bonfire.module.bpm.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListTodoQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.TodoVO;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * bpm任务
 *
 * @author Izneus
 * @since 2022-05-01
 */
@Api(tags = "工作流:待办任务")
@RequestMapping("/api/v1/bpm/task")
@RequiredArgsConstructor
@RestController
public class BpmTaskController {

    private final TaskService taskService;

    @AccessLog("我的待办")
    @ApiOperation("我的待办")
    @PostMapping("/todo")
    public BasePageVO<TodoVO> listTodos(@Validated @RequestBody ListTodoQuery query) {
        int limit = (query.getPageNum().intValue() - 1) * query.getPageSize().intValue();
        int offset = query.getPageSize().intValue();
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee(CurrentUserUtil.getUserId())
                .orderByTaskCreateTime().desc();
        long total = taskQuery.count();
        List<Task> tasks = taskQuery.listPage(limit, offset);

        List<TodoVO> rows = tasks.stream()
                .map(task -> BeanUtil.copyProperties(task, TodoVO.class))
                .collect(Collectors.toList());

        return new BasePageVO<>(total, query.getPageNum(), query.getPageSize(), rows);
    }

    @AccessLog("审批同意")
    @ApiOperation("审批同意")
    @PostMapping("/approve")
    public void approveTodo(@Validated @RequestBody IdQuery query) {
        taskService.complete(query.getId());
    }

    // todo 拒绝办理
    /*@AccessLog("审批拒绝")
    @ApiOperation("审批拒绝")
    @PostMapping("/reject")
    public void rejectTodo(@Validated @RequestBody IdQuery query) {
    }*/
}
