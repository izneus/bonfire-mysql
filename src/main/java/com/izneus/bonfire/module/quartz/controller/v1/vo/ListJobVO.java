package com.izneus.bonfire.module.quartz.controller.v1.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.BeanCopyUtil;
import com.izneus.bonfire.module.quartz.entity.SchedJobEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Izneus
 * @date 2020/11/06
 */
@ApiModel("任务列表VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class ListJobVO extends BasePageVO {

    @ApiModelProperty("任务列表")
    private List<JobItemVO> jobs;

    public ListJobVO(Page<SchedJobEntity> page) {
        super(page);
        jobs = BeanCopyUtil.copyListProperties(page.getRecords(), JobItemVO::new);
    }

}
