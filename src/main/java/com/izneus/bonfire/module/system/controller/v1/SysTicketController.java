package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.util.BeanCopyUtil;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ReplyTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.TicketQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListTicketVO;
import com.izneus.bonfire.module.system.controller.v1.vo.TicketItemVO;
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
    public ListTicketVO listTickets(@Validated ListTicketQuery query) {
        // 默认创建时间倒序查询
        if (StrUtil.isBlank(query.getOrderBy())) {
            query.setOrderBy("create_time desc");
        }
        Page<SysTicketEntity> page = ticketService.page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysTicketEntity>()
                        .eq(StrUtil.isNotBlank(query.getQuery()), SysTicketEntity::getTitle, query.getQuery())
                        .or()
                        .eq(StrUtil.isNotBlank(query.getQuery()), SysTicketEntity::getTicket, query.getQuery())
                        .apply("order by {0}", query.getOrderBy())
        );
        // 组装vo
        List<TicketItemVO> tickets = BeanCopyUtil.copyListProperties(page.getRecords(), TicketItemVO::new);
        return ListTicketVO.builder()
                .tickets(tickets)
                .pageNum(page.getCurrent())
                .pageSize(page.getSize())
                .totalSize(page.getTotal())
                .build();
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
