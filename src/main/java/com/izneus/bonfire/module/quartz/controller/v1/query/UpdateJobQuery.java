package com.izneus.bonfire.module.quartz.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Izneus
 * @date 2021/10/28
 */
@ApiModel("更新用户query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpdateJobQuery extends JobQuery {
    @ApiModelProperty("任务id")
    @NotBlank
    private String id;
}
