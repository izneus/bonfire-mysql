package com.izneus.bonfire.module.system.service;

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

}
