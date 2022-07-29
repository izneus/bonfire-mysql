package com.izneus.bonfire.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 这是分页返回的基类，比请求的基类多了一个totalSize属性以及分页参数的构造函数
 *
 * @author Izneus
 * @date 2020-07-29
 */
@ApiModel("分页查询返回数据基类")
@Data
@AllArgsConstructor
public class BasePageVO<T> {

    @ApiModelProperty("总数据条数")
    private Long totalSize;

    @ApiModelProperty("页码")
    private Long pageNum;

    @ApiModelProperty("一页的数据条数")
    private Long pageSize;

    @ApiModelProperty("分页数据行")
    private List<T> rows;

    public BasePageVO(Page page, List<T> rows) {
        this.totalSize = page.getTotal();
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.rows = rows;
    }

    public BasePageVO(Page<T> page) {
        this.totalSize = page.getTotal();
        this.pageNum = page.getCurrent();
        this.pageSize = page.getSize();
        this.rows = page.getRecords();
    }

}
