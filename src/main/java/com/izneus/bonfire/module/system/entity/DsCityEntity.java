package com.izneus.bonfire.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 多数据源测试表
 * </p>
 *
 * @author Izneus
 * @since 2021-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ds_city")
public class DsCityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 城市名称
     */
    private String city;


}
