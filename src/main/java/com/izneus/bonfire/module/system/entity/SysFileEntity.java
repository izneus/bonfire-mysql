package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 上传文件信息表
 * </p>
 *
 * @author Izneus
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
public class SysFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 文件名，特指上传之前的文件名
     */
    private String filename;

    /**
     * 哈希文件名，特指服务器保存在硬盘上的文件名，目前为uuid
     */
    private String uniqueFilename;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Long fileSize;

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
