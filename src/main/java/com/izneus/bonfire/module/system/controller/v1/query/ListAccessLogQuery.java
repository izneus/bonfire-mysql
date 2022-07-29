package com.izneus.bonfire.module.system.controller.v1.query;

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
 * @date 2021-03-11
 */
@ApiModel("操作日志列表query")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListAccessLogQuery extends BasePageQuery {
    @ApiModelProperty("接口名称")
    private String query;

    @ApiModelProperty("开始时间")
    private List<Date> createTime;

//    @ApiModelProperty("开始时间")
//    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date startTime;
//
//    @ApiModelProperty("结束时间")
//    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;
}
