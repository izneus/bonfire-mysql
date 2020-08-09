package com.izneus.bonfire.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 这是分页返回的基类，比请求的基类多了一个totalSize属性以及分页参数的构造函数
 *
 * @author Izneus
 * @date 2020/07/29
 */
@ApiModel("分页查询返回数据基类")
@Data
public class BasePageVO {

    @ApiModelProperty("总数据条数")
    private Long totalSize;

    @ApiModelProperty("页码")
    private Long pageNumber;

    @ApiModelProperty("一页的数据条数")
    private Long pageSize;

    public BasePageVO(Page page) {
        totalSize = page.getTotal();
        pageNumber = page.getCurrent();
        pageSize = page.getSize();
    }
}
