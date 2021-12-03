package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.module.system.controller.v1.query.*;
import com.izneus.bonfire.module.system.controller.v1.vo.*;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.service.SysDictService;
import com.izneus.bonfire.module.system.service.dto.DictDetailDTO;
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
 * 系统_字典 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-09-08
 */
@Api(tags = "系统:字典")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dict")
public class SysDictController {

    private final SysDictService dictService;

    @AccessLog("字典列表")
    @ApiOperation("字典列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:dict:list') or hasAuthority('admin')")
    public BasePageVO<ListDictVO> listDicts(@Validated @RequestBody ListDictQuery query) {
        Page<SysDictEntity> page = dictService.listDicts(query);
        List<ListDictVO> rows = page.getRecords().stream()
                .map(dict -> BeanUtil.copyProperties(dict, ListDictVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增字典")
    @ApiOperation("新增字典")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('sys:dict:create') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createDict(@Validated @RequestBody DictQuery dictQuery) {
        return new IdVO(dictService.createDict(dictQuery));
    }

    @AccessLog("字典详情")
    @ApiOperation("字典详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:dict:get') or hasAuthority('admin')")
    public GetDictVO getDictById(@Validated @RequestBody IdQuery query) {
        SysDictEntity dictEntity = dictService.getById(query.getId());
        if (dictEntity == null) {
            return null;
        }
        return BeanUtil.copyProperties(dictEntity, GetDictVO.class);
    }

    /// 字典一般没什么详情好看的
    /*@AccessLog("字典详情")
    @ApiOperation("字典详情")
    @GetMapping("/dicts/{id}")
    @PreAuthorize("hasAuthority('sys:dicts:get')")
    public GetDictVO getDictById(@NotBlank @PathVariable String id) {

    }*/

    @AccessLog("更新字典")
    @ApiOperation("更新字典")
    @PreAuthorize("hasAuthority('sys:dict:update') or hasAuthority('admin')")
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDictById(@Validated @RequestBody UpdateDictQuery query) {
        dictService.updateDictById(query);
    }

    @AccessLog("删除字典")
    @ApiOperation("删除字典")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:dict:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDict(@Validated @RequestBody IdQuery query) {
        dictService.deleteDictById(query.getId());
    }

    @AccessLog("批量删除字典")
    @ApiOperation("批量删除字典")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:dict:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDictById(@Validated @RequestBody IdsQuery query) {
        dictService.deleteDictsByIds(query.getIds());
    }

    @AccessLog("缓存字典")
    @ApiOperation("缓存字典")
    @PostMapping("/cache")
    public List<CacheDictVO> cacheDicts() {
        return dictService.cacheDicts();
    }

    @AccessLog("通过dictType获得字典详情")
    @ApiOperation("通过dictType获得字典详情")
    @PostMapping("/listByDictType")
    public DictDetailVO listDictDetails(@Validated @RequestBody DictTypeQuery query) {
        List<SysDictEntity> dicts = dictService.list(new LambdaQueryWrapper<SysDictEntity>()
                .eq(SysDictEntity::getDictType, query.getDictType())
                .eq(SysDictEntity::getStatus, Dict.DictStatus.OK.getCode())
                .orderByAsc(SysDictEntity::getDictSort));
        List<DictDetailDTO> details = dicts.stream()
                .map(dict -> BeanUtil.copyProperties(dict, DictDetailDTO.class))
                .collect(Collectors.toList());
        return DictDetailVO.builder()
                .details(details)
                .build();
    }


}
