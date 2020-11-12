package com.izneus.bonfire.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * 分页参数使用的基类
 *
 * @author Izneus
 * @date 2020/07/29
 */
@ApiModel("分页查询请求参数基类")
@Data
public class BasePageDTO {

    @ApiModelProperty("页码")
    @Min(value = 1)
    private Long pageNum;

    @ApiModelProperty("一页的数据条数")
    @Range(min = 1, max = 500)
    private Long pageSize;
}
