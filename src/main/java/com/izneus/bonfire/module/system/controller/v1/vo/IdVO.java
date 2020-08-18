package com.izneus.bonfire.module.system.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@AllArgsConstructor
@ApiModel("IdVO")
@Data
public class IdVO {
    private String id;
}
