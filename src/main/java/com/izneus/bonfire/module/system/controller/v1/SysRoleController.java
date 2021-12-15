package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.*;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListRoleVO;
import com.izneus.bonfire.module.system.controller.v1.vo.RoleVO;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService roleService;

    @AccessLog("角色列表")
    @ApiOperation("角色列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list') or hasAuthority('admin')")
    public BasePageVO<ListRoleVO> listRoles(@Validated @RequestBody ListRoleQuery query) {
        Page<SysRoleEntity> page = roleService.listRoles(query);
        // 组装vo
        List<ListRoleVO> rows = page.getRecords().stream()
                .map(role -> BeanUtil.copyProperties(role, ListRoleVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增角色")
    @ApiOperation("新增角色")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sys:role:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createRole(@Validated @RequestBody RoleQuery roleQuery) {
        String id = roleService.createRole(roleQuery);
        return new IdVO(id);
    }

    @AccessLog("角色详情")
    @ApiOperation("角色详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:role:get') or hasAuthority('admin')")
    public RoleVO getRoleById(@Validated @RequestBody IdQuery query) {
        return roleService.getRoleById(query.getId());
    }

    @AccessLog("更新角色")
    @ApiOperation("更新角色")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoleById(@Validated @RequestBody UpdateRoleQuery query) {
        roleService.updateRoleById(query);
    }

    @AccessLog("删除角色")
    @ApiOperation("删除角色")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoleById(@Validated @RequestBody IdQuery query) {
        roleService.deleteRoleById(query.getId());
    }

    @AccessLog("批量删除角色")
    @ApiOperation("批量删除角色")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:role:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@Validated @RequestBody IdsQuery query) {
        roleService.deleteRoleBatch(query.getIds());
    }


    @AccessLog("设置角色权限")
    @ApiOperation("设置角色权限")
    @PostMapping("/setAuth")
    @PreAuthorize("hasAnyAuthority('sys:role:create','sys:role:update') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setRoleAuthById(@Validated @RequestBody RoleAuthQuery query) {
        roleService.setRoleAuthById(query.getRoleId(), query.getAuthorityIds());
    }
}
