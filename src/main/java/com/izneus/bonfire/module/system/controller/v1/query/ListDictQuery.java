package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/09/08
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("字典列表query")
@Data
public class ListDictQuery extends BasePageDTO {
    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("状态")
    private String status;
}
