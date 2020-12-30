package com.izneus.bonfire.module.system.service;

import com.izneus.bonfire.module.system.controller.v1.query.NoticeQuery;
import com.izneus.bonfire.module.system.entity.SysNoticeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统通知表 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-12-10
 */
public interface SysNoticeService extends IService<SysNoticeEntity> {

    String createNotice(NoticeQuery notice);

    void deleteNoticeById(String id);

//    void getNoticeById(String id);
}
