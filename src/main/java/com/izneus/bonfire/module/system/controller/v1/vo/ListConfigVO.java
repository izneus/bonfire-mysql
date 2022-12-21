package com.izneus.bonfire.module.system.controller.v1.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2022-12-13
 */
@ApiModel("字典列表vo")
@Data
public class ListConfigVO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("设置项key")
    private String cfgKey;

    @ApiModelProperty("设置项value")
    private String cfgValue;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
