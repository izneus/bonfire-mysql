package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListUserQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UnlockQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.ExportVO;
import com.izneus.bonfire.module.system.controller.v1.vo.AuthUserVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListUserVO;
import com.izneus.bonfire.module.system.entity.DsCityEntity;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.DsCityService;
import com.izneus.bonfire.module.system.service.SysUserService;
import com.izneus.bonfire.module.system.controller.v1.vo.UserVO;
import com.izneus.bonfire.module.system.controller.v1.query.UserQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final SysUserService userService;
    private final JwtUtil jwtUtil;
    private final DsCityService cityService;

    @AccessLog("用户列表")
    @ApiOperation("用户列表")
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('sys:users:list')")
    public BasePageVO<ListUserVO> listUsers(@Validated ListUserQuery query) {
        // GET /users 一般用来返回简单的用户列表，比如单表查询，
        // 实际开发中可能会涉及复杂到丧心病狂的动态查询条件以及连表查询其他关联信息
        // 这种情况下可以考虑使用自定义动词，比如 POST /users:search 来解决
        Page<SysUserEntity> page = userService.listUsers(query);
        // 查询结果转vo
        List<ListUserVO> rows = page.getRecords().stream()
                .map(user -> BeanUtil.copyProperties(user, ListUserVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
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
    public IdVO createUser(@Validated @RequestBody UserQuery userQuery) {
        String id = userService.createUser(userQuery);
        return new IdVO(id);
    }

    @AccessLog("用户详情")
    @ApiOperation("用户详情")
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('sys:users:get')")
    public UserVO getUserById(@NotBlank @PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @AccessLog("更新用户")
    @ApiOperation("更新用户")
    @PreAuthorize("hasAuthority('sys:users:update')")
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserById(@NotBlank @PathVariable String userId,
                               @Validated @RequestBody UserQuery userQuery) {
        userService.updateUserById(userId, userQuery);
    }

    @AccessLog("删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('sys:users:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@NotBlank @PathVariable String userId) {
        userService.removeUserById(userId);
    }

    /// 暂时注释
    /*@ApiOperation("当前用户权限")
    @GetMapping("/user/authorities")
    public void listCurrentUserAuthoriries() {

    }*/

    @AccessLog("导出用户")
    @ApiOperation("导出用户")
    @PostMapping("/users:export")
    @PreAuthorize("hasAuthority('sys:users:export')")
    public ExportVO exportUsers(@Validated @RequestBody ListUserQuery query) {
        String filename = userService.exportUsers(query);
        Map<String, Object> claims = MapUtil.of("filename", filename);
        // 生成文件下载的临时token
        String token = jwtUtil.createToken(CurrentUserUtil.getUserId(), 5L, claims);
        return ExportVO.builder()
                .filename(filename)
                .token(token)
                .build();
    }

    @AccessLog("导入用户")
    @ApiOperation("导入用户")
    @PostMapping("/users:import")
    @PreAuthorize("hasAuthority('sys:users:import')")
    public void importUsers(@Validated @RequestBody IdQuery query) {
        // todo 谨慎开启导入功能
        // todo 太大的导入文件或是太复杂的导入逻辑，建议调度任务完成，否则会有长时间的执行时间，直观表现就是页面timeout等
        userService.importUsers(query.getId());
    }

    /**
     * 重置用户密码，一般是用户管理员使用
     *
     * @param query 用户id
     */
    @AccessLog("重置用户密码")
    @ApiOperation("重置用户密码")
    @PostMapping("/users:resetPassword")
    @PreAuthorize("hasAuthority('sys:users:resetPassword')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@Validated @RequestBody IdQuery query) {
        userService.resetPassword(query.getId());
    }

    /// redis里的锁定用户信息可以不做列表
    /*@AccessLog("锁定用户")
    @ApiOperation("锁定用户")
    @PostMapping("/users:listLocked")
    @PreAuthorize("hasAuthority('sys:users:listLocked')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void listLockedUsers() {
    }*/

    @AccessLog("解锁密码重试锁定用户")
    @ApiOperation("解锁密码重试锁定用户")
    @PostMapping("/users:unlock")
    @PreAuthorize("hasAuthority('sys:users:unlock')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlockUser(@Validated @RequestBody UnlockQuery query) {
        // todo 可以测试接收参数
        userService.unlockUser(query.getUsername());
    }

    @AccessLog("下线用户")
    @ApiOperation("下线用户")
    @PostMapping("/users:kickOut")
    @PreAuthorize("hasAuthority('sys:users:kickOut')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void kickOutUser(@Validated @RequestBody IdQuery query) {
        userService.kickOut(query.getId());
    }

    /**
     * 测试多数据源
     *
     * @return 测试数据
     */
    @GetMapping("/ds")
    @ApiOperation("测试多数据源")
    public List<DsCityEntity> testDs() {
        return cityService.list();
    }

    @AccessLog("当前认证用户信息")
    @ApiOperation("当前认证用户信息")
    @GetMapping("/user")
    public AuthUserVO getAuthUser() {
        String userId = CurrentUserUtil.getUserId();
        UserVO userVO = userService.getUserById(userId);
        return BeanUtil.copyProperties(userVO, AuthUserVO.class);
    }

}
