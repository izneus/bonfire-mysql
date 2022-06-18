package com.izneus.bonfire.module.bpm.service;

import com.izneus.bonfire.module.bpm.controller.v1.query.LeaveQuery;
import com.izneus.bonfire.module.bpm.entity.BpmOaLeaveEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 请假业务数据 服务类
 * </p>
 *
 * @author Izneus
 * @since 2022-06-14
 */
public interface BpmOaLeaveService extends IService<BpmOaLeaveEntity> {

    /**
     * 创建请假审批
     *
     * @param leaveQuery 业务字段
     * @return 请假业务表id
     */
    String createLeave(LeaveQuery leaveQuery);

}
