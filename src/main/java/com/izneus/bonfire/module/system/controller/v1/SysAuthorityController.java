package com.izneus.bonfire.module.system.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.query.CreateAuthQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListAuthQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.GetAuthVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListAuthVO;
import com.izneus.bonfire.module.system.entity.SysAuthorityEntity;
import com.izneus.bonfire.module.system.service.SysAuthorityService;
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

    private final SysAuthorityService sysAuthorityService;

    @AccessLog("权限列表")
    @ApiOperation("权限列表")
    @GetMapping("/authorities")
    @PreAuthorize("hasAuthority('sys:authorities:list')")
    public ListAuthVO listAuthorities(ListAuthQuery query) {
        Page<SysAuthorityEntity> page = sysAuthorityService.page(
                new Page<>(query.getPageNumber(), query.getPageSize()),
                new LambdaQueryWrapper<SysAuthorityEntity>()
                        .eq(StringUtils.hasText(query.getType()), SysAuthorityEntity::getType, query.getType())
                        .and(i -> i
                                .like(StringUtils.hasText(query.getQuery()),
                                        SysAuthorityEntity::getAuthority, query.getQuery())
                                .or()
                                .like(StringUtils.hasText(query.getQuery()),
                                        SysAuthorityEntity::getRemark, query.getQuery()))

        );
        return new ListAuthVO(page);
    }

    @AccessLog("新增权限")
    @ApiOperation("新增权限")
    @PostMapping("/authorities")
    @PreAuthorize("hasAuthority('sys:authorities:create')")
    public String createAuthority(CreateAuthQuery query) {
        SysAuthorityEntity authorityEntity = new SysAuthorityEntity();
        BeanUtils.copyProperties(query, authorityEntity);
        return sysAuthorityService.save(authorityEntity) ? authorityEntity.getId() : null;
    }

    @AccessLog("权限详情")
    @ApiOperation("权限详情")
    @GetMapping("/authorities/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:get')")
    public GetAuthVO getAuthorityById(@PathVariable String id) {
        SysAuthorityEntity authorityEntity = sysAuthorityService.getById(id);
        if (authorityEntity == null) {
            return null;
        }
        GetAuthVO authVO = new GetAuthVO();
        BeanUtils.copyProperties(authorityEntity, authVO);
        return authVO;
    }

    @AccessLog("更新权限")
    @ApiOperation("更新权限")
    @PutMapping("/authorities/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthorityById(@PathVariable String id, @Validated @RequestBody CreateAuthQuery query) {
        SysAuthorityEntity authorityEntity = new SysAuthorityEntity();
        BeanUtils.copyProperties(query, authorityEntity);
        authorityEntity.setId(id);
        sysAuthorityService.updateById(authorityEntity);
    }

    @AccessLog("删除权限")
    @ApiOperation("删除权限")
    @DeleteMapping("/authorities/{id}")
    @PreAuthorize("hasAuthority('sys:authorities:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorityById(@PathVariable String id) {
        sysAuthorityService.removeById(id);
    }
}
