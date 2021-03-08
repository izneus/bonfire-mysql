package com.izneus.bonfire.module.system.service;

import com.izneus.bonfire.module.system.controller.v1.vo.MonitorVO;

/**
 * @author Izneus
 * @date 2020/09/24
 */
public interface MonitorService {

    /**
     * 主机性能监控，包括cpu、内存、jvm、jre等
     *
     * @return MonitorVO
     */
    MonitorVO listMonitors();
}
