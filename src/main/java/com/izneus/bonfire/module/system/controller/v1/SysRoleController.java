package com.izneus.bonfire.module.system.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.query.SetRoleAuthQuery;
import com.izneus.bonfire.module.system.service.dto.RoleDTO;
import com.izneus.bonfire.module.system.controller.v1.query.ListRoleQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.GetRoleVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListRoleVO;
import com.izneus.bonfire.module.system.entity.SysRoleEntity;
import com.izneus.bonfire.module.system.service.SysRoleService;
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
    public ListRoleVO listRoles(ListRoleQuery query) {
        Page<SysRoleEntity> page = roleService.page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysRoleEntity>()
                        .like(StringUtils.hasText(query.getQuery()), SysRoleEntity::getRoleName, query.getQuery())
                        .or()
                        .like(StringUtils.hasText(query.getQuery()), SysRoleEntity::getRemark, query.getQuery())
        );
        return new ListRoleVO(page);
    }

    @AccessLog("新增角色")
    @ApiOperation("新增角色")
    @PostMapping("/roles")
    @PreAuthorize("hasAuthority('sys:roles:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createRole(@Validated @RequestBody RoleDTO roleDTO) {
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(roleDTO, roleEntity);
        String id = roleService.save(roleEntity) ? roleEntity.getId() : null;
        return new IdVO(id);
    }

    @AccessLog("角色详情")
    @ApiOperation("角色详情")
    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('sys:roles:get')")
    public GetRoleVO getRoleById(@PathVariable String id) {
        SysRoleEntity roleEntity = roleService.getById(id);
        if (roleEntity == null) {
            return null;
        }
        GetRoleVO roleVO = new GetRoleVO();
        BeanUtils.copyProperties(roleEntity, roleVO);
        return roleVO;
    }

    @AccessLog("更新角色")
    @ApiOperation("更新角色")
    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoleById(@PathVariable String id, @Validated @RequestBody RoleDTO roleDTO) {
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtils.copyProperties(roleDTO, roleEntity);
        roleEntity.setId(id);
        roleService.updateById(roleEntity);
    }

    @AccessLog("删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('sys:roles:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoleById(@PathVariable String id) {
        roleService.deleteRoleById(id);
    }

    @AccessLog("设置角色权限")
    @ApiOperation("设置角色权限")
    @PostMapping("/roles/{id}/authorities")
    @PreAuthorize("hasAnyAuthority('sys:roles:create','sys:roles:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setRoleAuthById(@PathVariable String id, @Validated @RequestBody SetRoleAuthQuery query) {
        roleService.setRoleAuthById(id, query.getAuthIds());
    }
}
