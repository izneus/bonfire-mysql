package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2020/09/09
 */
@ApiModel("字典列表dto")
@Data
public class DictQuery {
    @ApiModelProperty("字典id")
    private String id;

    @ApiModelProperty("字典类型")
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    @ApiModelProperty("字典标签")
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;

    @ApiModelProperty("字典值")
    @NotBlank(message = "字典值不能为空")
    private String dictValue;

    @ApiModelProperty("排序号")
    private String dictSort;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;
}
