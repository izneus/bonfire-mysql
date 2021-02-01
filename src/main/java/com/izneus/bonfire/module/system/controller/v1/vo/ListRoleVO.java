package com.izneus.bonfire.module.system.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.BeanCopyUtil;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.service.dto.ListRoleDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/08/14
 */
@ApiModel("角色列表vo")
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ListRoleVO extends BasePageVO {

    @ApiModelProperty("角色列表")
    private List<ListRoleDTO> roles;

    public ListRoleVO(Page<SysRoleEntity> page) {
        super(page);
        roles = BeanCopyUtil.copyListProperties(page.getRecords(), ListRoleDTO::new);
    }
}
