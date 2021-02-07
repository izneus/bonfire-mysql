package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统_字典
 * </p>
 *
 * @author Izneus
 * @since 2021-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_DICT")
public class SysDictEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * 字典类型
     */
    @TableField("DICT_TYPE")
    private String dictType;

    /**
     * 字典值（编码）
     */
    @TableField("DICT_VALUE")
    private String dictValue;

    /**
     * 字典文本
     */
    @TableField("DICT_LABEL")
    private String dictLabel;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新人
     */
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 字典状态，0=有效，1=无效
     */
    @TableField("STATUS")
    private String status;

    /**
     * 排序号
     */
    @TableField("DICT_SORT")
    private String dictSort;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;


}
