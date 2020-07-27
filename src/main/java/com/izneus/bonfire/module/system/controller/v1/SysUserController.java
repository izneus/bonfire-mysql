package com.izneus.bonfire.module.system.controller.v1;


import com.izneus.bonfire.module.system.controller.v1.query.CreateUserQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdateUserQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.CreateUserVO;
import com.izneus.bonfire.module.system.controller.v1.vo.EmptyVO;
import com.izneus.bonfire.module.system.controller.v1.vo.GetUserVO;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Api(tags = "系统：用户")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @ApiOperation("用户列表")
    @GetMapping("/users")
    public void listUsers() {
        // GET /users 一般用来返回简单的用户列表，比如单表查询，
        // 实际开发中可能会涉及复杂到丧心病狂的动态查询条件以及连表查询其他关联信息
        // 这种情况下可以考虑使用自定义动词，比如 POST /users:search 来解决
    }

    @ApiOperation("查询用户")
    @PostMapping("/users:search")
    public String searchUsers() {
        return "aaa";
    }

    @ApiOperation("新增用户")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserVO createUser(@Validated @RequestBody CreateUserQuery createUserQuery) {
        String id = sysUserService.createUser(createUserQuery);
        return new CreateUserVO(id);
    }

    @ApiOperation("查询用户详情")
    @GetMapping("/users/{userId}")
    public GetUserVO getUserById(@PathVariable String userId) {
        SysUserEntity userEntity = sysUserService.getById(userId);
        if (userEntity == null) {
            return null;
        }
        GetUserVO userVO = new GetUserVO();
        BeanUtils.copyProperties(userEntity, userVO);
        return userVO;
    }

    @ApiOperation("更新用户")
    @PutMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EmptyVO updateUserById(@PathVariable String userId,
                                  @Validated @RequestBody UpdateUserQuery updateUserQuery) {
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtils.copyProperties(updateUserQuery, userEntity);
        sysUserService.updateById(userEntity);
        return new EmptyVO();
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EmptyVO deleteUserById(@PathVariable String userId) {
        sysUserService.removeById(userId);
        return null;
    }


}
