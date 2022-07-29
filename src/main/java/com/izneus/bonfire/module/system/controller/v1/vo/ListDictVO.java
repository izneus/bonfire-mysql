package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020-09-08
 */
@ApiModel("字典列表vo")
@Data
public class ListDictVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典标签")
    private String dictLabel;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("排序号")
    private String dictSort;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
