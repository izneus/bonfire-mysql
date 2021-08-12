package com.izneus.bonfire.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-plus填充器，用来自动填充数据库审计类字段，比如创建时间、更新时间等，
 * 注意需要配合entity字段上的@TableField注解使用
 *
 * @author Izneus
 * @date 2021/01/11
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "createUser", String.class, CurrentUserUtil.getFillUserId());
        this.strictInsertFill(metaObject, "updateUser", String.class, CurrentUserUtil.getFillUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateUser", String.class, CurrentUserUtil.getFillUserId());

    }
}
