package com.izneus.bonfire.module.bpm.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.bpm.controller.v1.query.BpmModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.query.UpdateModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ListModelVO;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ModelVO;
import com.izneus.bonfire.module.bpm.service.BpmModelService;
import com.izneus.bonfire.module.system.controller.v1.query.IdQuery;
import com.izneus.bonfire.module.system.controller.v1.query.IdsQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * bpm模型
 *
 * @author Izneus
 * @since 2022-05-01
 */
@Api(tags = "工作流:模型定义")
@RequestMapping("/api/v1/bpm/model")
@RequiredArgsConstructor
@RestController
public class BpmModelController {

    private final BpmModelService bpmModelService;

    @AccessLog("模型列表")
    @ApiOperation("模型列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('bpm:model:list') or hasAuthority('admin')")
    public BasePageVO<ListModelVO> listModels(@Validated @RequestBody ListModelQuery query) {
        return bpmModelService.listModels(query);
    }

    @AccessLog("新建模型")
    @ApiOperation("新建模型")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('bpm:model:create') or hasAuthority('admin')")
    public IdVO createModel(@Validated @RequestBody BpmModelQuery bpmModelQuery) {
        String modelId = bpmModelService.createModel(bpmModelQuery);
        return new IdVO(modelId);
    }

    @AccessLog("模型详情")
    @ApiOperation("模型详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('bpm:model:get') or hasAuthority('admin')")
    public ModelVO getModel(@Validated @RequestBody IdQuery query) {
        return bpmModelService.getModel(query.getId());
    }

    @AccessLog("更新模型")
    @ApiOperation("更新模型")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('bpm:model:update') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateModel(@Validated @RequestBody UpdateModelQuery updateModelQuery) {
        bpmModelService.updateModel(updateModelQuery);
    }

    @AccessLog("删除模型")
    @ApiOperation("删除模型")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:model:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModel(@Validated @RequestBody IdQuery query) {
        bpmModelService.deleteModel(query.getId());
    }

    @AccessLog("批量删除模型")
    @ApiOperation("批量删除模型")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:model:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDeleteModel(@Validated @RequestBody IdsQuery query) {
        bpmModelService.batchDeleteModels(query.getIds());
    }

    @AccessLog("部署模型")
    @ApiOperation("部署模型")
    @PostMapping("/deploy")
    @PreAuthorize("hasAuthority('bpm:model:deploy') or hasAuthority('admin')")
    public String deployModel(@Validated @RequestBody IdQuery query) {
        return bpmModelService.deployModel(query.getId());
    }

}
