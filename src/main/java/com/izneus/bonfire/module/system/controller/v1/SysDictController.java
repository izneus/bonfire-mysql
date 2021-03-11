package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.ListDictQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.CacheDictVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListDictVO;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.service.SysDictService;
import com.izneus.bonfire.module.system.controller.v1.query.DictQuery;
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
 * 系统_字典 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-09-08
 */
@Api(tags = "系统:字典")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SysDictController {

    private final SysDictService dictService;

    @AccessLog("字典列表")
    @ApiOperation("字典列表")
    @GetMapping("/dicts")
    @PreAuthorize("hasAuthority('sys:dicts:list')")
    public BasePageVO<ListDictVO> listDicts(@Validated ListDictQuery query) {
        Page<SysDictEntity> page = dictService.listDicts(query);
        List<ListDictVO> rows = page.getRecords().stream()
                .map(dict -> BeanUtil.copyProperties(dict, ListDictVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("新增字典")
    @ApiOperation("新增字典")
    @PostMapping("/dicts")
    @PreAuthorize("hasAuthority('sys:dicts:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createDict(@Validated @RequestBody DictQuery dictQuery) {
        return new IdVO(dictService.createDict(dictQuery));
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
    @PreAuthorize("hasAuthority('sys:dicts:update')")
    @PutMapping("/dicts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDictById(@NotBlank @PathVariable String id,
                               @Validated @RequestBody DictQuery dictQuery) {
        dictService.updateDictById(id, dictQuery);
    }

    @AccessLog("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping("/dicts/{id}")
    @PreAuthorize("hasAuthority('sys:dicts:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDictById(@NotBlank @PathVariable String id) {
        dictService.deleteDictById(id);
    }

    @AccessLog("缓存字典")
    @ApiOperation("缓存字典")
    @PostMapping("/dicts:cache")
    @PreAuthorize("hasAuthority('sys:dicts:cache')")
    public List<CacheDictVO> cacheDicts() {
        // todo 更新和删除字典
        return dictService.cacheDicts();
    }


}
