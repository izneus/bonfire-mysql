package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.ListNoticeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.NoticeQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListNoticeVO;
import com.izneus.bonfire.module.system.entity.SysNoticeEntity;
import com.izneus.bonfire.module.system.service.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统通知表 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-12-10
 */
@RestController
@Api(tags = "系统:通知")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SysNoticeController {

    private final SysNoticeService noticeService;

    @AccessLog("通知列表")
    @ApiOperation("通知列表")
    @GetMapping("/notices")
    @PreAuthorize("hasAuthority('sys:notices:list')")
    public BasePageVO<ListNoticeVO> listNotices(@Validated ListNoticeQuery query) {
        Page<SysNoticeEntity> page = noticeService.listNotices(query);
        // 组装vo
        List<ListNoticeVO> rows = page.getRecords().stream()
                .map(notice -> BeanUtil.copyProperties(notice, ListNoticeVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增通知")
    @ApiOperation("新增通知")
    @PostMapping("/notices")
    @PreAuthorize("hasAuthority('sys:notices:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createNotice(@Validated @RequestBody NoticeQuery notice) {
        String noticeId = noticeService.createNotice(notice);
        return new IdVO(noticeId);
    }

    @AccessLog("删除通知")
    @ApiOperation("删除通知")
    @DeleteMapping("/notices/{id}")
    @PreAuthorize("hasAuthority('sys:notices:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoticeById(@NotBlank @PathVariable String id) {
        noticeService.deleteNoticeById(id);
    }

    /*@AccessLog("通知详情")
    @ApiOperation("通知详情")
    @GetMapping("/notices/{id}")
    @PreAuthorize("hasAuthority('sys:notices:get')")
    public void getNoticeById(@NotBlank @PathVariable String id) {
        noticeService.getById(id);
    }*/

}
