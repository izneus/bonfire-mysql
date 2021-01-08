package com.izneus.bonfire.module.system.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.BeanCopyUtil;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.dto.ListUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/07/29
 */
@ApiModel("用户列表vo")
@Data
@EqualsAndHashCode(callSuper = true)
public class ListUserVO extends BasePageVO {

    @ApiModelProperty("用户列表")
    private List<ListUserDTO> users;

    public ListUserVO(Page<SysUserEntity> page) {
        super(page);
        users = BeanCopyUtil.copyListProperties(page.getRecords(), ListUserDTO::new);
    }
}
