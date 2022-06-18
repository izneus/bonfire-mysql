package com.izneus.bonfire.module.bpm.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListProcessInstanceQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ListProcessInstanceVO;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ProcessInstanceVO;
import com.izneus.bonfire.module.bpm.service.BpmProcessInstanceService;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Izneus
 * @since 2022-06-01
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BpmProcessInstanceServiceImpl implements BpmProcessInstanceService {

    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final SysUserService userService;

    @Override
    public BasePageVO<ListProcessInstanceVO> listProcessInstances(ListProcessInstanceQuery query) {
        // todo 当前只查询运行时实例，历史实例没查询
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        if (StrUtil.isNotBlank(query.getStartUser())) {
            // 查询用户id
            SysUserEntity user = userService.getOne(new LambdaQueryWrapper<SysUserEntity>()
                    .eq(SysUserEntity::getUsername, query.getStartUser()));
            if (user == null) {
                // 查询不到用户，直接返回空数据
                return new BasePageVO<>(0L, query.getPageNum(), query.getPageSize(), null);
            }
            processInstanceQuery.startedBy(user.getId());
        }
        // 查询总数据量
        long total = processInstanceQuery.count();
        // 分页查询
        int limit = (query.getPageNum().intValue() - 1) * query.getPageSize().intValue();
        int offset = query.getPageSize().intValue();
        List<ProcessInstance> instances = processInstanceQuery.orderByStartTime().desc().listPage(limit, offset);
        // 组装vo
        List<ListProcessInstanceVO> vos = instances.stream().map(instance -> {
            ListProcessInstanceVO vo = new ListProcessInstanceVO();
            vo.setId(instance.getId());
            vo.setName(instance.getProcessDefinitionName());
            vo.setStartTime(instance.getStartTime());
            vo.setStartUserId(instance.getStartUserId());

            SysUserEntity user = userService.getById(instance.getStartUserId());
            if (user != null) {
                vo.setStartUser(user.getUsername());
            } else {
                vo.setStartUser("-");
            }
            vo.setIsEnded(instance.isEnded());
            return vo;
        }).collect(Collectors.toList());

        return new BasePageVO<>(total, query.getPageNum(), query.getPageSize(), vos);
    }

    @Override
    public ProcessInstanceVO getProcessInstance(String id) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
        String defId = instance.getProcessDefinitionId();
        String bpmnXml = IoUtil.readUtf8(repositoryService.getProcessModel(defId));
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(id)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();
        List<String> activityIds = activities.stream()
                .map(HistoricActivityInstance::getActivityId)
                .collect(Collectors.toList());
        return ProcessInstanceVO.builder()
                .id(instance.getId())
                .bpmnXml(bpmnXml)
                .activityIds(activityIds)
                .build();
    }

    /*@Override
    public List<HistoricActivityInstance> listActivitiesByProcessInstanceId(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime().asc()
                .list();
    }*/

}
