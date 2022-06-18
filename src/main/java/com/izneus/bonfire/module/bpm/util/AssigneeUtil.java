package com.izneus.bonfire.module.bpm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Flowable设置UserTask的Assignee。通过表达式调用
 *
 * @author Izneus
 * @since 2022-05-31
 */
@SuppressWarnings("unused")
@Component
@Slf4j
public class AssigneeUtil {

    /**
     * 返回admin用户id
     *
     * @return useId
     */
    public String getAssignee(String type) {
        log.info(type);
        // 在这里添加筛选逻辑，比如部门主管、公司负责人或者其他类型
        return "1";
    }

}
