package com.izneus.bonfire.module.bpm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.izneus.bonfire.module.bpm.controller.v1.query.LeaveQuery;
import com.izneus.bonfire.module.bpm.entity.BpmOaLeaveEntity;
import com.izneus.bonfire.module.bpm.mapper.BpmOaLeaveMapper;
import com.izneus.bonfire.module.bpm.service.BpmOaLeaveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 请假业务数据 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2022-06-14
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BpmOaLeaveServiceImpl extends ServiceImpl<BpmOaLeaveMapper, BpmOaLeaveEntity> implements BpmOaLeaveService {

    private final RuntimeService runtimeService;
    private final IdentityService identityService;

    @Override
    public String createLeave(LeaveQuery leaveQuery) {
        // 写业务表，获得business key
        BpmOaLeaveEntity leaveEntity = BeanUtil.copyProperties(leaveQuery, BpmOaLeaveEntity.class);
        save(leaveEntity);

        // 启动流程
        Map<String, Object> variables = new HashMap<>(16);
        long betweenDay = DateUtil.between(leaveQuery.getStartTime(), leaveQuery.getEndTime(), DateUnit.DAY);
        variables.put("duration", betweenDay);

        ProcessInstance processInstance;
        try {
            identityService.setAuthenticatedUserId(CurrentUserUtil.getUserId());
            processInstance = runtimeService.startProcessInstanceByKey(
                    "Process_OA_Leave", leaveEntity.getId(), variables);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        // 更新业务表的process_instance_id
        leaveEntity.setProcessInstanceId(processInstance.getProcessInstanceId());
        updateById(leaveEntity);

        return leaveEntity.getId();
    }

}
