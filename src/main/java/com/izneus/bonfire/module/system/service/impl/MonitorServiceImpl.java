package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.izneus.bonfire.module.system.service.MonitorService;
import com.izneus.bonfire.module.system.service.dto.MonitorDTO;
import org.springframework.stereotype.Service;
import oshi.util.FormatUtil;

/**
 * @author Izneus
 * @date 2020/09/24
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Override
    public MonitorDTO listMonitors() {
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
        MonitorDTO monitorDTO = new MonitorDTO();

        monitorDTO.setHostAddress(hostAddress);
        monitorDTO.setHostName(hostName);

        monitorDTO.setOsArch(osArch);
        monitorDTO.setOsName(osName);
        monitorDTO.setOsVersion(osVersion);

        monitorDTO.setCpuModel(cpuInfo.getCpuModel());
        monitorDTO.setCpuNum(cpuInfo.getCpuNum());
        monitorDTO.setCpuTotal(cpuInfo.getToTal());
        monitorDTO.setCpuFree(cpuInfo.getFree());
        monitorDTO.setCpuSys(cpuInfo.getSys());
        monitorDTO.setCpuUsed(cpuInfo.getUsed());
        monitorDTO.setCpuWait(cpuInfo.getWait());

        monitorDTO.setTotalMemory(FormatUtil.formatBytes(totalMemory));
        monitorDTO.setFreeMemory(FormatUtil.formatBytes(freeMemory));
        monitorDTO.setUsedMemory(FormatUtil.formatBytes(usedMemory));

        monitorDTO.setJvmTotalMemory(FormatUtil.formatBytes(jvmTotalMemory));
        monitorDTO.setJvmFreeMemory(FormatUtil.formatBytes(jvmFreeMemory));
        monitorDTO.setJvmName(jvmName);
        monitorDTO.setJvmVersion(jvmVersion);

        monitorDTO.setJreName(jreName);
        monitorDTO.setJreVersion(jreVersion);

        return monitorDTO;
    }
}
