package com.izneus.bonfire.module.system.controller.v1.vo;

import com.izneus.bonfire.module.system.service.dto.DictDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2021-08-01
 */
@Data
@ApiModel("字典详情vo")
@Builder
public class DictDetailVO {
    @ApiModelProperty("字典详情")
    private List<DictDetailDTO> details;
}
