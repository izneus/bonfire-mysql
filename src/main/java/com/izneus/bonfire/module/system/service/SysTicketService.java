package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.system.controller.v1.query.ListTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ReplyTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.TicketQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.TicketVO;
import com.izneus.bonfire.module.system.entity.SysTicketEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统工单表 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-12-31
 */
public interface SysTicketService extends IService<SysTicketEntity> {

    /**
     * 工单列表
     *
     * @param query 查询条件
     * @return 分页工单信息
     */
    Page<SysTicketEntity> listTickets(ListTicketQuery query);

    /**
     * 用户工单列表
     *
     * @param query  查询条件
     * @param userId 用户id
     * @return 分页工单信息
     */
    Page<SysTicketEntity> listTicketsByUserId(ListUserTicketQuery query, String userId);

    /**
     * 获取工单详情
     *
     * @param id 工单id
     * @return 工单详情
     */
    TicketVO getTicketById(String id);

    /**
     * 新建工单
     *
     * @param ticket ticket
     * @return 工单id，sys_ticket.id
     */
    String createTicket(TicketQuery ticket);

    /**
     * 返回新建的工单回复的id
     *
     * @param query query
     * @return 工单流id，sys_ticket_flow.id
     */
    String replyTicket(ReplyTicketQuery query);

    /**
     * 更新工单状态
     *
     * @param id     工单id
     * @param status 工单状态
     * @return boolean
     */
    boolean updateTicketStatus(String id, String status);

}
