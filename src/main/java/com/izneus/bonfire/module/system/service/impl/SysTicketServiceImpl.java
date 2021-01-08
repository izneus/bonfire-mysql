package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.module.security.CurrentUserUtil;
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
        String userId = CurrentUserUtil.getUser().getId();
        // 普通用户只能回复自己的工单，或者管理员回复任何工单
        SysTicketEntity ticket = getById(query.getTicketId());
        if (userId.equals(ticket.getCreateUser()) || CurrentUserUtil.hasRole("ADMIN")) {
            SysTicketFlowEntity flow = BeanUtil.copyProperties(query, SysTicketFlowEntity.class);
            flowService.save(flow);
            // todo 修改工单状态为正在处理
            return flow.getId();
        }
        return null;
    }
}
