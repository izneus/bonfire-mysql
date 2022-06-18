package com.izneus.bonfire.module.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 请假业务数据
 * </p>
 *
 * @author Izneus
 * @since 2022-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("bpm_oa_leave")
public class BpmOaLeaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk
     */
    private String id;

    /**
     * 流程实例id
     */
    private String processInstanceId;

    /**
     * 请假开始时间
     */
    private Date startTime;

    /**
     * 请假结束时间
     */
    private Date endTime;

    /**
     * 请假原因
     */
    private String reason;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    private String remark;


}
