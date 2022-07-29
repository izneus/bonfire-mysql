package com.izneus.bonfire.module.bpm.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020-07-20
 */
@ApiModel("流程实例列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListProcessInstanceQuery extends BasePageQuery {

    @ApiModelProperty(value = "发起人，使用用户名，唯一")
    private String startUser;

}
