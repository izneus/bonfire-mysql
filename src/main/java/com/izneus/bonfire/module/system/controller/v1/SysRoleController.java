package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.RoleAuthQuery;
import com.izneus.bonfire.module.system.controller.v1.query.RoleQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListRoleQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.RoleVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListRoleVO;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.service.SysRoleService;
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
 * 系统_角色 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
@Api(tags = "系统:角色")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @AccessLog("角色列表")
    @ApiOperation("角色列表")
    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('sys:roles:list')")
    public BasePageVO<ListRoleVO> listRoles(@Validated ListRoleQuery query) {
        Page<SysRoleEntity> page = roleService.listRoles(query);
        // 组装vo
        List<ListRoleVO> rows = page.getRecords().stream()
                .map(role -> BeanUtil.copyProperties(role, ListRoleVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增角色")
    @ApiOperation("新增角色")
    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('sys:roles:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createRole(@Validated @RequestBody RoleQuery roleQuery) {
        SysRoleEntity roleEntity = BeanUtil.copyProperties(roleQuery, SysRoleEntity.class);
        String id = roleService.save(roleEntity) ? roleEntity.getId() : null;
        return new IdVO(id);
    }

    @AccessLog("角色详情")
    @ApiOperation("角色详情")
    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('sys:roles:get')")
    public RoleVO getRoleById(@NotBlank @PathVariable String id) {
        SysRoleEntity roleEntity = roleService.getById(id);
        if (roleEntity == null) {
            return null;
        }
        return BeanUtil.copyProperties(roleEntity, RoleVO.class);
    }

    @AccessLog("更新角色")
    @ApiOperation("更新角色")
    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('sys:roles:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoleById(@NotBlank @PathVariable String id, @Validated @RequestBody RoleQuery roleQuery) {
        SysRoleEntity roleEntity = BeanUtil.copyProperties(roleQuery, SysRoleEntity.class);
        roleEntity.setId(id);
        roleService.updateById(roleEntity);
    }

    @AccessLog("删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('sys:roles:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoleById(@NotBlank @PathVariable String id) {
        roleService.deleteRoleById(id);
    }

    @AccessLog("设置角色权限")
    @ApiOperation("设置角色权限")
    @PostMapping("/roles/{id}/authorities")
    @PreAuthorize("hasAnyAuthority('sys:roles:create','sys:roles:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setRoleAuthById(@NotBlank @PathVariable String id, @Validated @RequestBody RoleAuthQuery query) {
        roleService.setRoleAuthById(id, query.getAuthIds());
    }
}
