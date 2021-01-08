package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.annotation.ValueOfEnum;
import com.izneus.bonfire.common.constant.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Izneus
 * @date 2020/12/04
 */
@Data
@ApiModel("通知query")
public class NoticeQuery {

    @ApiModelProperty(value = "接收者ids")
    private List<String> toIds;

    @ApiModelProperty(value = "通知标题", required = true)
    @NotBlank(message = "通知标题不能为空")
    private String title;

    @ApiModelProperty(value = "通知正文", required = true)
    @NotBlank(message = "通知正文不能为空")
    private String notice;

    @ApiModelProperty(value = "通知类型", required = true)
    @ValueOfEnum(enumClass = Dict.NoticeType.class)
    private String noticeType;

}
