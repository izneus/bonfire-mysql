package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.util.CommonUtil;
import com.izneus.bonfire.module.system.controller.v1.query.NoticeQuery;
import com.izneus.bonfire.module.system.entity.SysNoticeEntity;
import com.izneus.bonfire.module.system.entity.SysUserNoticeEntity;
import com.izneus.bonfire.module.system.mapper.SysNoticeMapper;
import com.izneus.bonfire.module.system.service.SysNoticeService;
import com.izneus.bonfire.module.system.service.SysUserNoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统通知表 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-12-10
 */
@Service
@RequiredArgsConstructor
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNoticeEntity> implements SysNoticeService {

    private final SysUserNoticeService userNoticeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createNotice(NoticeQuery notice) {
        // 先创建通知主体信息
        SysNoticeEntity noticeEntity = BeanUtil.copyProperties(notice, SysNoticeEntity.class);
        save(noticeEntity);

        /*
        创建用户通知关联，考虑普通企业开发，注册用户数量并不会太多，这里采用了最简单粗暴的方式，未做分表，
        对于全局通知，在用户拉取通知列表的时候，生成未读记录，
        对于点对点或者点对面的通知，在用户通知关联表里生成一条未读记录，你也可以根据实际情况优化生成逻辑
        */
        Dict.NoticeType noticeType = CommonUtil.getEnum(Dict.NoticeType.class, "value",
                notice.getNoticeType());
        switch (noticeType) {
            case GLOBAL:
                // 全局通知，暂时不插入记录，等用户get通知时候，生成一条未读记录插入用户通知表
                break;
            case PM:
                // 对特定用户(组)的系统消息
                List<SysUserNoticeEntity> userNotices = new ArrayList<>();
                List<String> ids = notice.getToIds();
                if (ids != null) {
                    for (String id : ids) {
                        SysUserNoticeEntity userNoticeEntity = new SysUserNoticeEntity();
                        userNoticeEntity.setUserId(id);
                        userNoticeEntity.setNoticeId(noticeEntity.getId());
                        userNoticeEntity.setStatus(Dict.UserNoticeStatus.UNREAD.getValue());
                        userNotices.add(userNoticeEntity);
                    }
                    userNoticeService.saveBatch(userNotices);
                }
                break;
            default:
        }

        return noticeEntity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNoticeById(String id) {
        // 同时删除sys_notice表的通知主体和sys_user_notice表的用户通知状态
        removeById(id);
        userNoticeService.remove(new LambdaQueryWrapper<SysUserNoticeEntity>()
                .eq(SysUserNoticeEntity::getNoticeId, id));
    }
}
