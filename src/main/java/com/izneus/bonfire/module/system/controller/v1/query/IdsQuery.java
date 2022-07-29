package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Izneus
 * @date 2021-08-31
 */
@Data
@ApiModel("通用idsQuery")
public class IdsQuery {
    @ApiModelProperty(value = "id列表", required = true)
    @NotNull
    @Size(min = 1, max = 500, message = "id列表长度应为1-500")
    private List<String> ids;
}
