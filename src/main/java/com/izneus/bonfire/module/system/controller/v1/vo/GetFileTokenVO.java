package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author Izneus
 * @date 2022-12-07
 */
@Data
@ApiModel("文件下载令牌vo")
@Builder
public class GetFileTokenVO {

    @ApiModelProperty("token")
    private String token;

}
