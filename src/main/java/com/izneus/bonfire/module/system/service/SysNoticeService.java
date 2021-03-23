package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.system.controller.v1.query.ListNoticeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserNoticeQuery;
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
    /**
     * 通知列表
     *
     * @param query 查询条件
     * @return 通知分页信息
     */
    Page<SysNoticeEntity> listNotices(ListNoticeQuery query);

    /**
     * 新建通知
     *
     * @param notice 通知
     * @return 通知id
     */
    String createNotice(NoticeQuery notice);

    /**
     * 删除通知
     *
     * @param id 通知id
     */
    void deleteNoticeById(String id);

    Page<SysNoticeEntity> listNoticesByUserId(ListUserNoticeQuery query);

//    void getNoticeById(String id);
}
