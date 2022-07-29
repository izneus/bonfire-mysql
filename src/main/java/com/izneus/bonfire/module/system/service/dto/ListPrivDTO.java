package com.izneus.bonfire.module.system.service.dto;

import lombok.Data;

/**
 * @author Izneus
 * @date 2020-08-10
 */
@Data
public class ListPrivDTO {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限字符串
     */
    private String privKey;
}
