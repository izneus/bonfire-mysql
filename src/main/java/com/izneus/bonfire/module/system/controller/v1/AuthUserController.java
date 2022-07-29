package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.constant.Constant;
import com.izneus.bonfire.common.util.RedisUtil;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import com.izneus.bonfire.module.system.controller.v1.query.ChangePasswordQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserNoticeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserTicketQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.ListNoticeVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListTicketVO;
import com.izneus.bonfire.module.system.controller.v1.vo.UserInfoVO;
import com.izneus.bonfire.module.system.controller.v1.vo.UserVO;
import com.izneus.bonfire.module.system.entity.SysNoticeEntity;
import com.izneus.bonfire.module.system.entity.SysTicketEntity;
import com.izneus.bonfire.module.system.service.SysNoticeService;
import com.izneus.bonfire.module.system.service.SysTicketService;
import com.izneus.bonfire.module.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/me")
public class AuthUserController {

    private final SysNoticeService noticeService;
    private final SysTicketService ticketService;
    private final SysUserService userService;
    private final RedisUtil redisUtil;

    @AccessLog("我的通知列表")
    @ApiOperation("我的通知列表")
    @PostMapping("/notice/list")
    public BasePageVO<ListNoticeVO> listNoticesByUserId(@Validated @RequestBody ListUserNoticeQuery query) {
        Page<SysNoticeEntity> page = noticeService.listNoticesByUserId(query);
        // 组装vo
        List<ListNoticeVO> rows = page.getRecords().stream()
                .map(notice -> BeanUtil.copyProperties(notice, ListNoticeVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("我的工单")
    @ApiOperation("我的工单")
    @PostMapping("/ticket/list")
    public BasePageVO<ListTicketVO> listTicketsByUserId(@Validated @RequestBody ListUserTicketQuery query) {
        Page<SysTicketEntity> page = ticketService.listTicketsByUserId(query, CurrentUserUtil.getUserId());
        // 组装vo
        List<ListTicketVO> rows = page.getRecords().stream()
                .map(notice -> BeanUtil.copyProperties(notice, ListTicketVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("我的信息")
    @ApiOperation("我的信息")
    @PostMapping("/info")
    public UserInfoVO getUserInfo() {
        // 获得redis里登录时组装好的角色+权限字符串
        String userId = CurrentUserUtil.getUserId();
        String key = StrUtil.format(Constant.RedisKey.PRIVILEGE, userId);
        String authString = (String) redisUtil.get(key);
        // 按照前端要求，字符串切分后转数组，包含角色和权限
        List<String> roles = StrUtil.split(authString, ',');

        UserVO user = userService.getUserById(userId);

        return UserInfoVO.builder()
                .id(userId)
                .fullname(user.getFullname())
                .username(user.getUsername())
                .roles(roles)
                .build();
    }

    @AccessLog("修改密码")
    @ApiOperation("修改密码")
    @PostMapping("/changePassword")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@Validated @RequestBody ChangePasswordQuery query) {
        userService.changePassword(query);
    }

}
