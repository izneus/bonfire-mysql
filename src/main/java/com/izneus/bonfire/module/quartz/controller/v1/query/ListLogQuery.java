package com.izneus.bonfire.module.quartz.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/11/10
 */
@ApiModel("调度任务列表ListLogQuery")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListLogQuery extends BasePageQuery {
    @ApiModelProperty("开始时间")
    private List<Date> createTime;
}
