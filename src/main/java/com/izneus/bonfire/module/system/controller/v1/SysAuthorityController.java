package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.AuthQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListAuthQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.AuthVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListAuthVO;
import com.izneus.bonfire.module.system.entity.SysAuthorityEntity;
import com.izneus.bonfire.module.system.service.SysAuthorityService;
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
 * 系统_权限 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-08-10
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SysAuthorityController {

    private final SysAuthorityService authService;

    @AccessLog("权限列表")
    @ApiOperation("权限列表")
    @GetMapping("/authorities")
    @PreAuthorize("hasAuthority('sys:authorities:list')")
    public BasePageVO<ListAuthVO> listAuthorities(@Validated ListAuthQuery query) {
        Page<SysAuthorityEntity> page = authService.listAuthorities(query);
        // 组装vo
        List<ListAuthVO> rows = page.getRecords().stream().map(auth -> BeanUtil.copyProperties(auth, ListAuthVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增权限")
    @ApiOperation("新增权限")
    @PostMapping("/authorities")
    @PreAuthorize("hasAuthority('sys:authorities:create')")
    public IdVO createAuthority(@Validated @RequestBody AuthQuery authQuery) {
        SysAuthorityEntity authorityEntity = BeanUtil.copyProperties(authQuery, SysAuthorityEntity.class);
        String id = authService.save(authorityEntity) ? authorityEntity.getId() : null;
        return new IdVO(id);
    }

    @AccessLog("权限详情")
    @ApiOperation("权限详情")
    @GetMapping("/authorities/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:get')")
    public AuthVO getAuthorityById(@NotBlank @PathVariable String id) {
        SysAuthorityEntity authorityEntity = authService.getById(id);
        if (authorityEntity == null) {
            return null;
        }
        return BeanUtil.copyProperties(authorityEntity, AuthVO.class);
    }

    @AccessLog("更新权限")
    @ApiOperation("更新权限")
    @PutMapping("/authorities/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthorityById(@NotBlank @PathVariable String id, @Validated @RequestBody AuthQuery authQuery) {
        SysAuthorityEntity authorityEntity = BeanUtil.copyProperties(authQuery, SysAuthorityEntity.class);
        authorityEntity.setId(id);
        authService.updateById(authorityEntity);
    }

    @AccessLog("删除权限")
    @ApiOperation("删除权限")
    @DeleteMapping("/authorities/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorityById(@NotBlank @PathVariable String id) {
        authService.removeById(id);
    }
}
