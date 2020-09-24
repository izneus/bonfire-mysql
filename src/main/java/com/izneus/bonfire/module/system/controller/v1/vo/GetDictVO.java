package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/09/09
 */
@Data
public class GetDictVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典编码")
    private String dictCode;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("排序号")
    private String dictSort;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
