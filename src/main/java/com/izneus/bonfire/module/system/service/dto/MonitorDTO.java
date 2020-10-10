package com.izneus.bonfire.module.system.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 软硬件监控信息，按需添加
 *
 * @author Izneus
 * @date 2020/10/10
 */
@ApiModel("监控详情")
@Data
public class MonitorDTO {
    @ApiModelProperty("主机名")
    private String hostName;

    @ApiModelProperty("主机地址")
    private String hostAddress;

    @ApiModelProperty("系统架构")
    private String osArch;

    @ApiModelProperty("系统名称")
    private String osName;

    @ApiModelProperty("系统版本")
    private String osVersion;

    @ApiModelProperty("cpu核心数")
    private Integer cpuNum;

    @ApiModelProperty("cpu型号信息")
    private String cpuModel;

    @ApiModelProperty("cpu用户使用率")
    private Double cpuUsed;

    @ApiModelProperty("cpu总使用率")
    private Double cpuTotal;

    @ApiModelProperty("cpu系统使用率")
    private Double cpuSys;

    @ApiModelProperty("cpu当前等待率")
    private Double cpuWait;

    @ApiModelProperty("cpu当前空闲率")
    private Double cpuFree;

    @ApiModelProperty("jre名称")
    private String jreName;

    @ApiModelProperty("jre版本")
    private String jreVersion;

    @ApiModelProperty("总物理内存")
    private String totalMemory;

    @ApiModelProperty("空闲内存")
    private String freeMemory;

    @ApiModelProperty("使用内存")
    private String usedMemory;

    @ApiModelProperty("jvm名称")
    private String jvmName;

    @ApiModelProperty("jvm版本")
    private String jvmVersion;

    @ApiModelProperty("jvm总内存")
    private String jvmTotalMemory;

    @ApiModelProperty("jvm空闲内存")
    private String jvmFreeMemory;
}
