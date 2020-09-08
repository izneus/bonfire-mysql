package com.izneus.bonfire.module.system.service.dto;

import lombok.Data;

/**
 * @author Izneus
 * @date 2020/08/10
 */
@Data
public class ListAuthDTO {
    private String userId;
    private String roleName;
    private String authority;
}
