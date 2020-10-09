package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.system.SystemUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.izneus.bonfire.module.system.service.MonitorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor.TickType;
import oshi.util.Util;

/**
 * @author Izneus
 * @date 2020/09/24
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    @Override
    public void listMonitors() {
        // 主机名，系统，ip，系统架构等
        String hostAddress = SystemUtil.getHostInfo().getAddress();
        String hostName = SystemUtil.getHostInfo().getName();
        String osArch = SystemUtil.getOsInfo().getArch();
        String osName = SystemUtil.getOsInfo().getName();
        String osVersion = SystemUtil.getOsInfo().getVersion();

        // todo cpu用hutool
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();

        // cpu
        long[] prevTicks = OshiUtil.getProcessor().getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = OshiUtil.getProcessor().getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
//        cpu.setCpuNum(OshiUtil.getProcessor().getLogicalProcessorCount());
//        cpu.setTotal(totalCpu);
//        cpu.setSys(cSys);
//        cpu.setUsed(user);
//        cpu.setWait(iowait);
//        cpu.setFree(idle);

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


    }
}
