package com.izneus.bonfire.module.system.controller.v1;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.query.ListDictQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.GetDictVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListDictVO;
import com.izneus.bonfire.module.system.entity.SysDictEntity;
import com.izneus.bonfire.module.system.service.SysDictService;
import com.izneus.bonfire.module.system.service.dto.DictDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

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
    public ListDictVO listDicts(ListDictQuery query) {
        Page<SysDictEntity> page = dictService.page(
                new Page<>(query.getPageNumber(), query.getPageSize()),
                new LambdaQueryWrapper<SysDictEntity>()
                        .like(StringUtils.hasText(query.getDictType()),
                                SysDictEntity::getDictType, query.getDictType())
                        .like(StringUtils.hasText(query.getDictValue()),
                                SysDictEntity::getDictValue, query.getDictValue())
                        .like(StringUtils.hasText(query.getStatus()),
                                SysDictEntity::getStatus, query.getStatus())
        );
        return new ListDictVO(page);
    }

    @AccessLog("新增字段")
    @ApiOperation("新增字典")
    @PostMapping("/dicts")
    @PreAuthorize("hasAuthority('sys:dicts:create')")
    @ResponseStatus(HttpStatus.CREATED)
    public IdVO createDict(@Validated @RequestBody DictDTO dictDTO) {
        SysDictEntity dictEntity = new SysDictEntity();
        BeanUtil.copyProperties(dictDTO, dictEntity);
        dictService.save(dictEntity);
        return new IdVO(dictEntity.getId());
    }

    /// 字典一般没什么详情好看的
    /*@AccessLog("用户详情")
    @ApiOperation("用户详情")
    @GetMapping("/dicts/{id}")
    @PreAuthorize("hasAuthority('sys:users:get')")
    public GetDictVO getDictById(@NotBlank @PathVariable String id) {

    }*/

    @AccessLog("更新字典")
    @ApiOperation("更新字典")
    @PreAuthorize("hasAuthority('sys:dicts:update')")
    @PutMapping("/dicts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDictById(@NotBlank @PathVariable String id,
                               @Validated @RequestBody DictDTO dictDTO) {
        SysDictEntity dictEntity = new SysDictEntity();
        BeanUtil.copyProperties(dictDTO, dictEntity);
        dictEntity.setId(id);
        dictService.updateById(dictEntity);
    }

    @AccessLog("删除字典")
    @ApiOperation("删除字典")
    @DeleteMapping("/dicts/{id}")
    @PreAuthorize("hasAuthority('sys:dicts:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDictById(@NotBlank @PathVariable String id) {
        dictService.removeById(id);
    }

}
