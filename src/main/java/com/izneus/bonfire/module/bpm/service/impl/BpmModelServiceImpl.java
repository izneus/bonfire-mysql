package com.izneus.bonfire.module.bpm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.module.bpm.controller.v1.query.BpmModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.query.UpdateModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ListModelVO;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ModelVO;
import com.izneus.bonfire.module.bpm.service.BpmModelService;
import com.izneus.bonfire.module.bpm.service.dto.ModelMetaInfoDTO;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Izneus
 * @since 2022-05-09
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BpmModelServiceImpl implements BpmModelService {

    private final RepositoryService repositoryService;

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Override
    public String createModel(BpmModelQuery bpmModelQuery) {
        // 先判断一下key是否已经存在
        Model createdModel = repositoryService.createModelQuery().modelKey(bpmModelQuery.getKey()).singleResult();
        if (createdModel != null) {
            // 模型已经存在
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "当前Key已经存在模型定义");
        }
        // 创建并保存模型
        Model model = repositoryService.newModel();
        saveOrUpdateModel(model, bpmModelQuery);
        return model.getId();
    }

    private void saveOrUpdateModel(Model model, BpmModelQuery query) {
        model.setName(query.getName());
        model.setKey(query.getKey());
        // 设置metaInfo
        ModelMetaInfoDTO metaInfoDTO = new ModelMetaInfoDTO();
        metaInfoDTO.setDescription(query.getDescription());
        model.setMetaInfo(JSONUtil.toJsonStr(metaInfoDTO));
        repositoryService.saveModel(model);
        // 保存bpmn xml
        String bpmnXml = query.getBpmnXml();
        if (StrUtil.isNotBlank(bpmnXml)) {
            repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(bpmnXml));
        }
    }

    @Override
    public void updateModel(UpdateModelQuery updateModelQuery) {
        // 用id查询一下模型信息
        Model createdModel = repositoryService.getModel(updateModelQuery.getId());
        if (createdModel == null) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "当前id无模型定义");
        }
        saveOrUpdateModel(createdModel, updateModelQuery);
    }

    @Override
    public BasePageVO<ListModelVO> listModels(ListModelQuery query) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StrUtil.isNotBlank(query.getQuery())) {
            // 关键字模糊匹配模型名称
            modelQuery.modelNameLike("%" + query.getQuery() + "%");
        }
        // 查询总数据量
        long total = modelQuery.count();

        // 分页查询
        int limit = (query.getPageNum().intValue() - 1) * query.getPageSize().intValue();
        int offset = query.getPageSize().intValue();
        List<Model> models = modelQuery.orderByCreateTime().desc().listPage(limit, offset);
        // 组装vo，拼接其他流程相关信息
        List<ListModelVO> rows = models.stream()
                .map(model -> {
                    ListModelVO vo = BeanUtil.copyProperties(model, ListModelVO.class);
                    ModelMetaInfoDTO metaInfoDTO = JSONUtil.toBean(model.getMetaInfo(), ModelMetaInfoDTO.class);
                    vo.setDescription(metaInfoDTO.getDescription());
                    return vo;
                })
                .collect(Collectors.toList());
        // 部署相关信息
        Set<String> deploymentIds = new HashSet<>();
        for (Model model : models) {
            if (model.getDeploymentId() != null) {
                deploymentIds.add(model.getDeploymentId());
            }
        }
        // 流程定义信息
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery()
                .deploymentIds(deploymentIds).latestVersion().list();
        for (ProcessDefinition definition : definitions) {
            for (ListModelVO vo : rows) {
                if (definition.getDeploymentId().equals(vo.getDeploymentId())) {
                    // 流程部署id相等，拼接数据
                    vo.setVersion(definition.getVersion());
                }
            }
        }

        return new BasePageVO<>(total, query.getPageNum(), query.getPageSize(), rows);
    }

    @Override
    public void deleteModel(String id) {
        repositoryService.deleteModel(id);
    }

    @Override
    public void batchDeleteModels(List<String> ids) {
        for (String id : ids) {
            deleteModel(id);
        }
    }

    @Override
    public ModelVO getModel(String id) {
        Model model = repositoryService.getModel(id);
        if (model == null) {
            return null;
        }
        byte[] bpmnBytes = repositoryService.getModelEditorSource(id);
        String bpmnXml = StrUtil.utf8Str(bpmnBytes);
        return ModelVO.builder()
                .id(model.getId())
                .bpmnXml(bpmnXml)
                .build();
    }

    @Override
    public String deployModel(String id) {
        Model model = repositoryService.getModel(id);
        byte[] bpmnBytes = repositoryService.getModelEditorSource(id);
        Deployment deployment = repositoryService.createDeployment()
                .key(model.getKey())
                .name(model.getName())
                .addBytes(model.getKey() + BPMN_FILE_SUFFIX, bpmnBytes)
                .deploy();
        // 把deploymentId更新回model，方便后续查询，不清楚是不是必要操作
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
        return deployment.getId();
    }

}
