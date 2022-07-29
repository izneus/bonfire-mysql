package com.izneus.bonfire.module.bpm.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 待办任务query
 *
 * @author Izneus
 * @date 2022-06-14
 */
@ApiModel("待办任务query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListTodoQuery extends BasePageQuery {
}
