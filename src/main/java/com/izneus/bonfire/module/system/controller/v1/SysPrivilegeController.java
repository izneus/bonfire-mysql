package com.izneus.bonfire.module.system.controller.v1;


import com.izneus.bonfire.common.annotation.AccessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统_权限 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2022-01-19
 */
@Api(tags = "系统:权限")
@RestController
@RequestMapping("/api/v1/privilege")
public class SysPrivilegeController {

//    @AccessLog("用户列表")
//    @ApiOperation("用户列表")
//    @PostMapping("/list")
//    @PreAuthorize("hasAuthority('sys:priv:list') or hasAuthority('admin')")
//    public BasePageVO<ListUserVO> listUsers(@Validated @RequestBody ListUserQuery query) {
//        // 新版本api请求说明：因为五花八门的原因，系统的所有请求，禁止使用PUT/DELETE/OPTION等不常用方法
//        // 除了少数幂等的请求采用GET，其他请求，特别是业务请求，全部采用POST
//        // 而所有之前采用的复数请求命名，全部统一为单数，函数命名的单复数不做规定
//        Page<SysUserEntity> page = userService.listUsers(query);
//        // 查询结果转vo
//        List<ListUserVO> rows = page.getRecords().stream()
//                .map(user -> BeanUtil.copyProperties(user, ListUserVO.class))
//                .collect(Collectors.toList());
//        return new BasePageVO<>(page, rows);
//    }

}
