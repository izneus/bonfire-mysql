package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.module.system.controller.v1.vo.*;
import com.izneus.bonfire.module.system.entity.SysPrivilegeEntity;
import com.izneus.bonfire.module.system.service.SysPrivilegeService;
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
 * @since 2022-01-19
 */
@Api(tags = "系统:权限")
@RestController
@RequestMapping("/api/v1/privilege")
@RequiredArgsConstructor
public class SysPrivilegeController {

    private final SysPrivilegeService privilegeService;

    @AccessLog("权限树")
    @ApiOperation("权限树")
    @PostMapping("/getPrivilegeTree")
    @PreAuthorize("hasAuthority('sys:priv:list') or hasAuthority('admin')")
    public GetPrivTreeVO getPrivilegeTree() {
        return GetPrivTreeVO.builder().privilegeTree(privilegeService.getPrivilegeTree()).build();
    }

    @AccessLog("权限列表")
    @ApiOperation("权限列表")
    @PostMapping("/getPrivilegeList")
    @PreAuthorize("hasAuthority('sys:priv:list') or hasAuthority('admin')")
    public BasePageVO<ListPrivVO> getPrivilegeList(@Validated @RequestBody ListPrivilegeQuery query) {
        Page<SysPrivilegeEntity> page = privilegeService.getPrivilegeList(query);
        // 组装vo
        List<ListPrivVO> rows = page.getRecords().stream().map(priv -> BeanUtil.copyProperties(priv, ListPrivVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增权限")
    @ApiOperation("新增权限")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sys:privilege:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createPrivilege(@Validated @RequestBody PrivilegeQuery privilegeQuery) {
        System.err.println(privilegeQuery.getParentId());
        String id = privilegeService.createPrivilege(privilegeQuery);
        return new IdVO(id);
    }

    @AccessLog("权限详情")
    @ApiOperation("权限详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:privilege:get') or hasAuthority('admin')")
    public PrivilegeVO getPrivilegeById(@Validated @RequestBody IdQuery query) {
        return privilegeService.getPrivilegeById(query.getId());
    }

    @AccessLog("更新权限")
    @ApiOperation("更新权限")
    @PreAuthorize("hasAuthority('sys:privilege:update') or hasAuthority('admin')")
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePrivilegeById(@Validated @RequestBody UpdatePrivilegeQuery query) {
        privilegeService.updatePrivilegeById(query);
    }

    @AccessLog("删除权限")
    @ApiOperation("删除权限")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:privilege:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrivilegeById(@Validated @RequestBody IdQuery query) {
        privilegeService.removePrivilegeById(query.getId());
    }

    @AccessLog("批量删除权限")
    @ApiOperation("批量删除权限")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:privilege:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@Validated @RequestBody IdsQuery query) {
        privilegeService.deletePrivilegeBatch(query.getIds());
    }

}
