package com.izneus.bonfire.module.system.controller.v1.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Izneus
 * @date 2021-08-22
 */
@ApiModel("批量删除用户query")
@Data
public class DeleteUserBatchQuery {
    @ApiModelProperty("用户id列表")
    @NotNull
    @Size(min = 1, max = 500, message = "id列表最小长度为1，最大长度为500")
    private List<String> ids;
}
