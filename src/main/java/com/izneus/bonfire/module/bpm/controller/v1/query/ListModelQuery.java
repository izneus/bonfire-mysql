package com.izneus.bonfire.module.bpm.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/07/20
 */
@ApiModel("bpm模型列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListModelQuery extends BasePageQuery {

    @ApiModelProperty(value = "关键字，模糊匹配name，精确匹配key")
    private String query;

}
