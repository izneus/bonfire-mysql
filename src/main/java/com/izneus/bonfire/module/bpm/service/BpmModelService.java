package com.izneus.bonfire.module.bpm.service;

import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.bpm.controller.v1.query.BpmModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.query.ListModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.query.UpdateModelQuery;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ListModelVO;
import com.izneus.bonfire.module.bpm.controller.v1.vo.ModelVO;

import java.util.List;

/**
 * 模型相关业务
 *
 * @author Izneus
 * @since 2022-05-09
 */
public interface BpmModelService {

    /**
     * 创建模型
     *
     * @param bpmModelQuery query
     * @return 模型id
     */
    String createModel(BpmModelQuery bpmModelQuery);

    /**
     * 更新模型
     *
     * @param updateModelQuery query
     */
    void updateModel(UpdateModelQuery updateModelQuery);

    /**
     * 查询模型列表
     *
     * @param query query
     * @return 分页模型信息
     */
    BasePageVO<ListModelVO> listModels(ListModelQuery query);

    /**
     * 删除模型
     *
     * @param id 模型id
     */
    void deleteModel(String id);

    /**
     * 批量删除模型
     *
     * @param ids 模型id列表
     */
    void batchDeleteModels(List<String> ids);

    /**
     * 模型详情
     *
     * @param id 模型id
     * @return 模型vo
     */
    ModelVO getModel(String id);

    /**
     * 部署模型
     *
     * @param id 模型id
     * @return 部署id，deploymentId
     */
    String deployModel(String id);

}
