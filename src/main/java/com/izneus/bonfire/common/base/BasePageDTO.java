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

    @ApiModelProperty(value = "页码", required = true)
    @Min(value = 1, message = "页码为大于0的整数")
    private Long pageNum;

    @ApiModelProperty(value = "一页的数据条数", required = true)
    @Range(min = 1, max = 500, message = "一页数据条数为1～500条")
    private Long pageSize;
}
