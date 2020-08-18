package com.izneus.bonfire.module.system.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.BeanCopyUtils;
import com.izneus.bonfire.module.system.entity.SysAuthorityEntity;
import com.izneus.bonfire.module.system.service.dto.ListAuthDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/08/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ListAuthVO extends BasePageVO {

    private List<ListAuthDTO> authorities;

    public ListAuthVO(Page<SysAuthorityEntity> page) {
        super(page);
        authorities = BeanCopyUtils.copyListProperties(page.getRecords(), ListAuthDTO::new);
    }
}
