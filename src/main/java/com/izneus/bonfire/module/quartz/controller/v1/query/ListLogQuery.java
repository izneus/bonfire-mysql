package com.izneus.bonfire.module.quartz.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/11/10
 */
@ApiModel("调度任务列表ListLogQuery")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListLogQuery extends BasePageDTO {
}
