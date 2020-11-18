package com.izneus.bonfire.module.quartz.controller.v1.vo;

import com.izneus.bonfire.common.base.BasePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/11/12
 */
@ApiModel("任务日志列表VO")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ListLogVO extends BasePageVO {
    @ApiModelProperty("任务日志列表")
    private List<LogItemVO> logs;
}
