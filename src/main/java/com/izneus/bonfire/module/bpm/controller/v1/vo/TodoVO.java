package com.izneus.bonfire.module.bpm.controller.v1.vo;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Izneus
 * @date 2022/06/14
 */
@ApiModel("待办任务vo")
@Data
@Builder
public class TodoVO {

    private String id;
    private String name;
    private Date createTime;
    private String processInstanceId;

}
