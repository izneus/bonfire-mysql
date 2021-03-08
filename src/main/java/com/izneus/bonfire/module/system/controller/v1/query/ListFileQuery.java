package com.izneus.bonfire.module.system.controller.v1.query;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2020/09/22
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel("文件列表query")
@Data
public class ListFileQuery extends BasePageQuery {

    @ApiModelProperty("查询参数，模糊匹配文件名或备注")
    private String query;

    @ApiModelProperty("创建起止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<Date> createTimes;
}
