package com.izneus.bonfire.module.system.controller.v1.vo;

import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/18
 */
@Data
public class GetAuthVO {
    private String id;
    private String authority;
    private String remark;
    private String type;
}
