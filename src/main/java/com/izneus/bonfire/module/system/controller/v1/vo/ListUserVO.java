package com.izneus.bonfire.module.system.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.util.BasePageVO;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Iznejus
 * @date 2020/07/29
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户列表vo")
@Data
public class ListUserVO extends BasePageVO {

    private List<SysUserEntity> users;

    public ListUserVO(Page<SysUserEntity> page) {
        super(page);
        users = page.getRecords();
    }
}
