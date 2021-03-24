package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import com.izneus.bonfire.module.system.controller.v1.query.ListTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ReplyTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.query.TicketQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.TicketVO;
import com.izneus.bonfire.module.system.entity.SysTicketEntity;
import com.izneus.bonfire.module.system.entity.SysTicketFlowEntity;
import com.izneus.bonfire.module.system.mapper.SysTicketFlowMapper;
import com.izneus.bonfire.module.system.mapper.SysTicketMapper;
import com.izneus.bonfire.module.system.service.SysTicketFlowService;
import com.izneus.bonfire.module.system.service.SysTicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.service.dto.FlowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统工单表 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-12-31
 */
@Service
@RequiredArgsConstructor
public class SysTicketServiceImpl extends ServiceImpl<SysTicketMapper, SysTicketEntity> implements SysTicketService {

    private final SysTicketFlowMapper flowMapper;
    private final SysTicketFlowService flowService;

    @Override
    public Page<SysTicketEntity> listTickets(ListTicketQuery query) {
        // 默认创建时间倒序查询
        if (StrUtil.isBlank(query.getOrderBy())) {
            query.setOrderBy("create_time desc");
        }
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysTicketEntity>()
                        .eq(StrUtil.isNotBlank(query.getQuery()), SysTicketEntity::getTitle, query.getQuery())
                        .or()
                        .eq(StrUtil.isNotBlank(query.getQuery()), SysTicketEntity::getTicket, query.getQuery())
                        .apply("order by {0}", query.getOrderBy())
        );
    }

    @Override
    public Page<SysTicketEntity> listTicketsByUserId(ListUserTicketQuery query, String userId) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysTicketEntity>()
                        .eq(SysTicketEntity::getCreateUser, userId)
                        .orderByDesc(SysTicketEntity::getCreateTime)
        );
    }

    @Override
    public TicketVO getTicketById(String id) {
        SysTicketEntity ticket = getById(id);
        List<FlowDTO> flows = flowMapper.listFlowsByTicketId(id);
        // 组装vo
        TicketVO vo = BeanUtil.copyProperties(ticket, TicketVO.class);
        vo.setFlows(flows);
        return vo;
    }

    @Override
    public String createTicket(TicketQuery ticket) {
        SysTicketEntity entity = BeanUtil.copyProperties(ticket, SysTicketEntity.class);
        entity.setStatus(Dict.TicketStatus.PENDING.getValue());
        save(entity);
        return entity.getId();
    }

    @Override
    public String replyTicket(ReplyTicketQuery query) {
        // 不能回复已经关闭的工单
        SysTicketEntity ticket = getById(query.getTicketId());
        if (Dict.TicketStatus.CLOSED.getValue().equals(ticket.getStatus())) {
            throw new BadRequestException(ErrorCode.FAILED_PRECONDITION, "工单已经关闭");
        }
        String userId = CurrentUserUtil.getUser().getId();
        // 普通用户只能回复自己的工单，或者管理员回复任何工单
        if (userId.equals(ticket.getCreateUser()) || CurrentUserUtil.hasRole("ADMIN")) {
            SysTicketFlowEntity flow = BeanUtil.copyProperties(query, SysTicketFlowEntity.class);
            flowService.save(flow);
            // 未处理的工单状态改为处理中
            if (Dict.TicketStatus.PENDING.getValue().equals(ticket.getStatus())) {
                updateTicketStatus(query.getTicketId(), Dict.TicketStatus.PROCESSING.getValue());
            }
            return flow.getId();
        }
        return null;
    }

    @Override
    public boolean updateTicketStatus(String id, String status) {
        SysTicketEntity entity = new SysTicketEntity();
        entity.setId(id);
        entity.setStatus(status);
        return updateById(entity);
    }
}
