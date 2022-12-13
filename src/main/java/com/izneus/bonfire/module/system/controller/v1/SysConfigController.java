package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.*;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListConfigVO;
import com.izneus.bonfire.module.system.entity.SysConfigEntity;
import com.izneus.bonfire.module.system.service.SysConfigService;
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
 * 系统 设置 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2022-12-13
 */
@Api(tags = "系统:设置")
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
@RestController
public class SysConfigController {

    private final SysConfigService configService;

    @AccessLog("设置项列表")
    @ApiOperation("设置项列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:config:list') or hasAuthority('admin')")
    public BasePageVO<ListConfigVO> listConfigs(@Validated @RequestBody ListConfigQuery query) {
        Page<SysConfigEntity> page = configService.page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysConfigEntity>()
                        .like(StrUtil.isNotBlank(query.getCfgKey()), SysConfigEntity::getCfgKey, query.getCfgKey())
                        .orderByAsc(SysConfigEntity::getCfgKey)
        );
        List<ListConfigVO> rows = page.getRecords().stream()
                .map(i -> BeanUtil.copyProperties(i, ListConfigVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增设置")
    @ApiOperation("新增设置")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sys:config:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createConfig(@Validated @RequestBody ConfigQuery query) {
        SysConfigEntity configEntity = BeanUtil.copyProperties(query, SysConfigEntity.class);
        configService.save(configEntity);
        return new IdVO(configEntity.getId());
    }

    @AccessLog("更新设置")
    @ApiOperation("更新设置")
    @PreAuthorize("hasAuthority('sys:config:update') or hasAuthority('admin')")
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConfigById(@Validated @RequestBody UpdateConfigQuery query) {
        SysConfigEntity configEntity = BeanUtil.copyProperties(query, SysConfigEntity.class);
        configService.updateById(configEntity);
    }

    @AccessLog("批量删除设置")
    @ApiOperation("批量删除设置")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:config:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@Validated @RequestBody IdsQuery query) {
        configService.removeByIds(query.getIds());
    }

    @AccessLog("删除设置")
    @ApiOperation("删除设置")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:config:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConfigById(@Validated @RequestBody IdQuery query) {
        configService.removeById(query.getId());
    }

}
