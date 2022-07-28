package com.izneus.bonfire.module.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分片文件表
 * </p>
 *
 * @author Izneus
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file_chunk")
public class SysFileChunkEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk
     */
    private String id;

    /**
     * 当前块的次序，第一个块是 1，注意不是从 0 开始的
     */
    private Long chunkNumber;

    /**
     * 分片大小，单位
     */
    private BigDecimal chunkSize;

    /**
     * 当前块的大小，实际大小
     */
    private BigDecimal currentChunkSize;

    /**
     * 文件总大小
     */
    private Long totalSize;

    /**
     * 每个文件的唯一标示
     */
    private String identifier;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件夹上传的时候文件的相对路径属性
     */
    private String relativePath;

    /**
     * 总分片数
     */
    private Long totalChunks;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 备注
     */
    private String remark;


}
