package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("SYS_FILE")
public class SysFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("ID")
    private String id;

    /**
     * 文件名，特指上传之前的文件名
     */
    @TableField("FILENAME")
    private String filename;

    /**
     * 哈希文件名，特指服务器保存在硬盘上的文件名，目前为uuid
     */
    @TableField("UNIQUE_FILENAME")
    private String uniqueFilename;

    /**
     * 后缀
     */
    @TableField("SUFFIX")
    private String suffix;

    /**
     * 文件路径
     */
    @TableField("PATH")
    private String path;

    /**
     * 文件大小
     */
    @TableField("FILE_SIZE")
    private Long fileSize;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_USER")
    private String updateUser;

    /**
     * 备注
     */
    @TableField("REMARK")
    private String remark;


}
