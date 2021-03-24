package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ReplyTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.TicketQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListTicketVO;
import com.izneus.bonfire.module.system.controller.v1.vo.TicketVO;
import com.izneus.bonfire.module.system.entity.SysTicketEntity;
import com.izneus.bonfire.module.system.service.SysTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统工单表 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-12-31
 */
@RestController
@Api(tags = "系统:工单")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SysTicketController {

    private final SysTicketService ticketService;

    @AccessLog("工单列表")
    @ApiOperation("工单列表")
    @GetMapping("/tickets")
    @PreAuthorize("hasAuthority('sys:tickets:list')")
    public BasePageVO<ListTicketVO> listTickets(@Validated ListTicketQuery query) {
        Page<SysTicketEntity> page = ticketService.listTickets(query);
        // 组装vo
        List<ListTicketVO> rows = page.getRecords().stream()
                .map(ticket -> BeanUtil.copyProperties(ticket, ListTicketVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增工单")
    @ApiOperation("新增工单")
    @PostMapping("/tickets")
    @PreAuthorize("hasAuthority('sys:tickets:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createUser(@Validated @RequestBody TicketQuery query) {
        String id = ticketService.createTicket(query);
        return new IdVO(id);
    }

    @AccessLog("工单详情")
    @ApiOperation("工单详情")
    @GetMapping("/tickets/{id}")
    @PreAuthorize("hasAuthority('sys:tickets:get')")
    public TicketVO getTicketById(@NotBlank @PathVariable String id) {
        return ticketService.getTicketById(id);
    }

    @AccessLog("回复工单")
    @ApiOperation("回复工单")
    @PostMapping("/tickets:reply")
    @PreAuthorize("hasAuthority('sys:tickets:reply')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void replyTicket(@Validated @RequestBody ReplyTicketQuery query) {
        ticketService.replyTicket(query);
    }

    @AccessLog("关闭工单")
    @ApiOperation("关闭工单")
    @PostMapping("/tickets:close")
    @PreAuthorize("hasAuthority('sys:tickets:close')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeTicket(@Validated @RequestBody IdQuery query) {
        ticketService.updateTicketStatus(query.getId(), Dict.TicketStatus.CLOSED.getValue());
    }


}
