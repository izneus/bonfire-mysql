package com.izneus.bonfire.module.bpm.service;

import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListProcessInstanceQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ListProcessInstanceVO;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ProcessInstanceVO;

/**
 * @author Izneus
 * @since 2022-06-01
 */
public interface BpmProcessInstanceService {

    /**
     * 流程实例列表
     *
     * @param query 查询条件
     * @return 分页的流程实例信息
     */
    BasePageVO<ListProcessInstanceVO> listProcessInstances(ListProcessInstanceQuery query);

    /**
     * 流程实例详情
     *
     * @param id 实例id
     * @return 实例详情
     */
    ProcessInstanceVO getProcessInstance(String id);

    // List<HistoricActivityInstance> listActivitiesByProcessInstanceId(String processInstanceId);

}
