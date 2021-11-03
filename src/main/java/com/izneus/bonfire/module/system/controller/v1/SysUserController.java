package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.security.CurrentUserUtil;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.query.*;
import com.izneus.bonfire.module.system.controller.v1.vo.ExportVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListUserVO;
import com.izneus.bonfire.module.system.controller.v1.vo.UserVO;
import com.izneus.bonfire.module.system.entity.DsCityEntity;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.DsCityService;
import com.izneus.bonfire.module.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class SysUserController {

    private final SysUserService userService;
    private final JwtUtil jwtUtil;
    private final DsCityService cityService;

    @AccessLog("用户列表")
    @ApiOperation("用户列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list') or hasAuthority('admin')")
    public BasePageVO<ListUserVO> listUsers(@Validated @RequestBody ListUserQuery query) {
        // 新版本api请求说明：因为五花八门的原因，系统的所有请求，禁止使用PUT/DELETE/OPTION等不常用方法
        // 除了少数幂等的请求采用GET，其他请求，特别是业务请求，全部采用POST
        // 而所有之前采用的复数请求命名，全部统一为单数，函数命名的单复数不做规定
        Page<SysUserEntity> page = userService.listUsers(query);
        // 查询结果转vo
        List<ListUserVO> rows = page.getRecords().stream()
                .map(user -> BeanUtil.copyProperties(user, ListUserVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    /// 已经修改api规范，下面风格先注释
    /*@AccessLog("查询用户")
    @ApiOperation("查询用户")
    @PostMapping("/users:search")
    @PreAuthorize("hasAuthority('sys:users:search')")
    public String searchUsers() {
        return "users:search";
    }*/

    @AccessLog("新增用户")
    @ApiOperation("新增用户")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sys:user:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createUser(@Validated @RequestBody UserQuery userQuery) {
        String id = userService.createUser(userQuery);
        return new IdVO(id);
    }

    @AccessLog("用户详情")
    @ApiOperation("用户详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:user:get') or hasAuthority('admin')")
    public UserVO getUserById(@Validated @RequestBody IdQuery query) {
        return userService.getUserById(query.getId());
    }

    @AccessLog("更新用户")
    @ApiOperation("更新用户")
    @PreAuthorize("hasAuthority('sys:user:update') or hasAuthority('admin')")
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserById(@Validated @RequestBody UpdateUserQuery query) {
        userService.updateUserById(query);
    }

    @AccessLog("删除用户")
    @ApiOperation("删除用户")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@Validated @RequestBody IdQuery query) {
        userService.removeUserById(query.getId());
    }

    @AccessLog("批量删除用户")
    @ApiOperation("批量删除用户")
    @PostMapping("/deleteBatch")
    @PreAuthorize("hasAuthority('sys:user:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(@Validated @RequestBody IdsQuery query) {
        userService.deleteUserBatch(query.getIds());
    }

    /// 暂时注释
    /*@ApiOperation("当前用户权限")
    @GetMapping("/user/authorities")
    public void listCurrentUserAuthoriries() {

    }*/

    @AccessLog("导出用户")
    @ApiOperation("导出用户")
    @PostMapping("/export")
    @PreAuthorize("hasAuthority('sys:user:export') or hasAuthority('admin')")
    public ExportVO exportUsers(@Validated @RequestBody ListUserQuery query) {
        String filename = userService.exportUsers(query);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("filename", filename);
        claims.put("fileType", "1");
        // 生成文件下载的临时token
        String token = jwtUtil.createToken(CurrentUserUtil.getUserId(), 120L, claims);
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
     * @param query 用户id列表
     */
    @AccessLog("重置用户密码")
    @ApiOperation("重置用户密码")
    @PostMapping("/resetPasswordBatch")
    @PreAuthorize("hasAuthority('sys:user:resetPassword') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPasswordBatch(@Validated @RequestBody IdsQuery query) {
        userService.resetPasswordBatch(query.getIds());
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

    /*@AccessLog("当前认证用户信息")
    @ApiOperation("当前认证用户信息")
    @GetMapping("/user")
    public AuthUserVO getAuthUser() {
        String userId = CurrentUserUtil.getUserId();
        UserVO userVO = userService.getUserById(userId);
        return BeanUtil.copyProperties(userVO, AuthUserVO.class);
    }*/

}
