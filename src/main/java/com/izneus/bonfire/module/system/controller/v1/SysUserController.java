package com.izneus.bonfire.module.system.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.query.CreateUserQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdateUserQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.GetUserVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListUserVO;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统_用户 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-06-28
 */
@Api(tags = "系统:用户")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class SysUserController {

    private final SysUserService sysUserService;

    @AccessLog("用户列表")
    @ApiOperation("用户列表")
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('sys:users:list')")
    public ListUserVO listUsers(ListUserQuery query) {
        // GET /users 一般用来返回简单的用户列表，比如单表查询，
        // 实际开发中可能会涉及复杂到丧心病狂的动态查询条件以及连表查询其他关联信息
        // 这种情况下可以考虑使用自定义动词，比如 POST /users:search 来解决
        Page<SysUserEntity> page = sysUserService.page(
                new Page<>(query.getPageNumber(), query.getPageSize()),
                new LambdaQueryWrapper<SysUserEntity>()
                        .like(StringUtils.hasText(query.getUsername()),
                                SysUserEntity::getUsername, query.getUsername())
        );
        return new ListUserVO(page);
    }

    @AccessLog("查询用户")
    @ApiOperation("查询用户")
    @PostMapping("/users:search")
    @PreAuthorize("hasAuthority('sys:users:search')")
    public String searchUsers() {
        return "users:search";
    }

    @AccessLog("新增用户")
    @ApiOperation("新增用户")
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('sys:users:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createUser(@Validated @RequestBody CreateUserQuery createUserQuery) {
        String id = sysUserService.createUser(createUserQuery);
        return new IdVO(id);
    }

    @AccessLog("用户详情")
    @ApiOperation("查询用户详情")
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('sys:users:get')")
    public GetUserVO getUserById(@PathVariable String userId) {
        SysUserEntity userEntity = sysUserService.getById(userId);
        if (userEntity == null) {
            return null;
        }
        GetUserVO userVO = new GetUserVO();
        BeanUtils.copyProperties(userEntity, userVO);
        return userVO;
    }

    @AccessLog("更新用户")
    @ApiOperation("更新用户")
    @PreAuthorize("hasAuthority('sys:users:update')")
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserById(@PathVariable String userId,
                               @Validated @RequestBody UpdateUserQuery updateUserQuery) {
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(updateUserQuery, userEntity);
        userEntity.setId(userId);
        sysUserService.updateById(userEntity);
    }

    @AccessLog("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('sys:users:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable String userId) {
        sysUserService.removeById(userId);
    }

    @ApiOperation("当前用户权限")
    @GetMapping("/user/authorities")
    public void listCurrentUserAuthoriries() {

    }

}
