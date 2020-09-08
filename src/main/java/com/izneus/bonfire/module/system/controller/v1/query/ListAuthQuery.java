package com.izneus.bonfire.module.system.controller.v1.query;

import com.izneus.bonfire.common.base.BasePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ListAuthQuery extends BasePageDTO {
    private String query;
    private String type;
}
