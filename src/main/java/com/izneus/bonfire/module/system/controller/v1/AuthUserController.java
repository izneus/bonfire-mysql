package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserNoticeQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.ListNoticeVO;
import com.izneus.bonfire.module.system.entity.SysNoticeEntity;
import com.izneus.bonfire.module.system.service.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 当前认证用户controller，基本是查询一些"我的"信息
 *
 * @author Izneus
 * @date 2021/03/17
 */
@Api(tags = "系统:当前认证用户")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthUserController {

    private final SysNoticeService noticeService;

    @AccessLog("我的通知列表")
    @ApiOperation("我的通知列表")
    @GetMapping("/user/notices")
    public BasePageVO<ListNoticeVO> listNoticesByUserId(@Validated ListUserNoticeQuery query) {
        Page<SysNoticeEntity> page = noticeService.listNoticesByUserId(query);
        // 组装vo
        List<ListNoticeVO> rows = page.getRecords().stream()
                .map(notice -> BeanUtil.copyProperties(notice, ListNoticeVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }
}
