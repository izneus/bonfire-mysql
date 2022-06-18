package com.izneus.bonfire.module.bpm.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2022/05/25
 */
@ApiModel("更新模型query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateModelQuery extends BpmModelQuery {
    @ApiModelProperty("模型id")
    @NotBlank
    private String id;
}
