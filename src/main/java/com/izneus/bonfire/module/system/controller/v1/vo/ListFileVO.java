package com.izneus.bonfire.module.system.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.BeanCopyUtil;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.izneus.bonfire.module.system.service.dto.ListFileDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/09/22
 */
@ApiModel("文件列表vo")
@EqualsAndHashCode(callSuper = true)
@Data
public class ListFileVO extends BasePageVO {
    @ApiModelProperty("用户列表")
    private List<ListFileDTO> files;

    public ListFileVO(Page<SysFileEntity> page) {
        super(page);
        files = BeanCopyUtil.copyListProperties(page.getRecords(), ListFileDTO::new);
    }
}
