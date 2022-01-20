package com.izneus.bonfire.module.system.controller.v1.vo;

import com.izneus.bonfire.module.system.service.dto.PrivTreeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Izneus
 * @date 2022/01/20
 */
@ApiModel("权限树vo")
@Data
@Builder
public class GetPrivTreeVO {
    @ApiModelProperty("权限树")
    private List<PrivTreeDTO> privilegeTree;
}
