package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.izneus.bonfire.module.system.service.MonitorService;
import com.izneus.bonfire.module.system.controller.v1.vo.MonitorVO;
import org.springframework.stereotype.Service;
import oshi.util.FormatUtil;

/**
 * @author Izneus
 * @date 2020/09/24
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Override
    public MonitorVO listMonitors() {
        // 主机名，系统，ip，系统架构等
        String hostAddress = SystemUtil.getHostInfo().getAddress();
        String hostName = SystemUtil.getHostInfo().getName();
        String osArch = SystemUtil.getOsInfo().getArch();
        String osName = SystemUtil.getOsInfo().getName();
        String osVersion = SystemUtil.getOsInfo().getVersion();

        // cpu相关
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();

        // 内存
        long totalMemory = OshiUtil.getMemory().getTotal();
        long freeMemory = OshiUtil.getMemory().getAvailable();
        long usedMemory = totalMemory - freeMemory;

        // jvm
        long jvmFreeMemory = SystemUtil.getRuntimeInfo().getFreeMemory();
        long jvmTotalMemory = SystemUtil.getRuntimeInfo().getTotalMemory();
        String jvmName = SystemUtil.getJvmInfo().getName();
        String jvmVersion = SystemUtil.getJvmInfo().getVersion();

        // jre
        String jreName = SystemUtil.getJavaRuntimeInfo().getName();
        String jreVersion = SystemUtil.getJavaRuntimeInfo().getVersion();

        // 开始各种set填充返回
        MonitorVO monitorVO = new MonitorVO();

        monitorVO.setHostAddress(hostAddress);
        monitorVO.setHostName(hostName);

        monitorVO.setOsArch(osArch);
        monitorVO.setOsName(osName);
        monitorVO.setOsVersion(osVersion);

        String cpuModel = cpuInfo.getCpuModel();
        String cpu = cpuModel.substring(0, cpuModel.indexOf("CPU")+3);
        monitorVO.setCpuModel(cpu);

        monitorVO.setCpuNum(cpuInfo.getCpuNum());
        monitorVO.setCpuTotal(cpuInfo.getToTal());
        monitorVO.setCpuFree(cpuInfo.getFree());
        monitorVO.setCpuSys(cpuInfo.getSys());
        monitorVO.setCpuUsed(cpuInfo.getUsed());
        monitorVO.setCpuWait(cpuInfo.getWait());

        monitorVO.setTotalMemory(FormatUtil.formatBytes(totalMemory));
        monitorVO.setFreeMemory(FormatUtil.formatBytes(freeMemory));
        monitorVO.setUsedMemory(FormatUtil.formatBytes(usedMemory));

        monitorVO.setJvmTotalMemory(FormatUtil.formatBytes(jvmTotalMemory));
        monitorVO.setJvmFreeMemory(FormatUtil.formatBytes(jvmFreeMemory));
        monitorVO.setJvmName(jvmName);
        monitorVO.setJvmVersion(jvmVersion);

        monitorVO.setJreName(jreName);
        monitorVO.setJreVersion(jreVersion);

        return monitorVO;
    }
}
