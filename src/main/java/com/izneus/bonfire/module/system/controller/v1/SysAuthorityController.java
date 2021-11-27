package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.AuthQuery;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListAuthQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdateAuthQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.AuthVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListAuthVO;
import com.izneus.bonfire.module.system.entity.SysAuthorityEntity;
import com.izneus.bonfire.module.system.service.SysAuthorityService;
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
 * 系统_权限 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
@Api(tags = "系统:权限")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class SysAuthorityController {

    private final SysAuthorityService authService;

    @AccessLog("权限列表")
    @ApiOperation("权限列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:auth:list') or hasAuthority('admin')")
    public BasePageVO<ListAuthVO> listAuthorities(@Validated @RequestBody ListAuthQuery query) {
        Page<SysAuthorityEntity> page = authService.listAuthorities(query);
        // 组装vo
        List<ListAuthVO> rows = page.getRecords().stream().map(auth -> BeanUtil.copyProperties(auth, ListAuthVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增权限")
    @ApiOperation("新增权限")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sys:auth:create') or hasAuthority('admin')")
    public IdVO createAuthority(@Validated @RequestBody AuthQuery authQuery) {
        SysAuthorityEntity authorityEntity = BeanUtil.copyProperties(authQuery, SysAuthorityEntity.class);
        String id = authService.save(authorityEntity) ? authorityEntity.getId() : null;
        return new IdVO(id);
    }

    @AccessLog("权限详情")
    @ApiOperation("权限详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:auth:get') or hasAuthority('admin')")
    public AuthVO getAuthorityById(@Validated @RequestBody IdQuery query) {
        SysAuthorityEntity authorityEntity = authService.getById(query.getId());
        if (authorityEntity == null) {
            return null;
        }
        return BeanUtil.copyProperties(authorityEntity, AuthVO.class);
    }

    @AccessLog("更新权限")
    @ApiOperation("更新权限")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:auth:update') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthorityById(@Validated @RequestBody UpdateAuthQuery query) {
        SysAuthorityEntity authorityEntity = BeanUtil.copyProperties(query, SysAuthorityEntity.class);
        authorityEntity.setId(query.getId());
        authService.updateById(authorityEntity);
    }

    @AccessLog("删除权限")
    @ApiOperation("删除权限")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:auth:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorityById(@Validated @RequestBody IdQuery query) {
        authService.removeById(query.getId());
    }
}
