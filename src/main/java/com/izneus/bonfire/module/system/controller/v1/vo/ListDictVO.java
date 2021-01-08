package com.izneus.bonfire.module.system.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.BeanCopyUtil;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.service.dto.ListDictDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/09/08
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("字典列表vo")
@Data
public class ListDictVO extends BasePageVO {
    @ApiModelProperty("字典列表")
    private List<ListDictDTO> dicts;

    public ListDictVO(Page<SysDictEntity> page) {
        super(page);
        dicts = BeanCopyUtil.copyListProperties(page.getRecords(), ListDictDTO::new);
    }
}
