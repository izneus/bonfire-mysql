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
    @ApiModelProperty("字典类型")
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    @ApiModelProperty("字典编码")
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

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
