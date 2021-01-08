package com.izneus.bonfire.module.system.mapper;

import com.izneus.bonfire.module.system.entity.SysTicketFlowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izneus.bonfire.module.system.service.dto.FlowDTO;

import java.util.List;

/**
 * <p>
 * 工单回复信息 Mapper 接口
 * </p>
 *
 * @author Izneus
 * @since 2021-01-05
 */
public interface SysTicketFlowMapper extends BaseMapper<SysTicketFlowEntity> {

    /**
     * 返回工单流内容
     *
     * @param ticketId 工单id
     * @return FLowDTO
     */
    List<FlowDTO> listFlowsByTicketId(String ticketId);

}
