package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.module.system.controller.v1.query.ListPrivilegeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.PrivilegeQuery;
import com.izneus.bonfire.module.system.controller.v1.query.UpdatePrivilegeQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.PrivilegeVO;
import com.izneus.bonfire.module.system.entity.SysPrivilegeEntity;
import com.izneus.bonfire.module.system.mapper.SysPrivilegeMapper;
import com.izneus.bonfire.module.system.service.SysPrivilegeService;
import com.izneus.bonfire.module.system.service.dto.PrivTreeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统_权限 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2022-01-19
 */
@Service
@RequiredArgsConstructor
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilegeEntity> implements SysPrivilegeService {

    private final SysPrivilegeMapper sysPrivilegeMapper;

    @Override
    public List<PrivTreeDTO> getPrivilegeTree() {
        // noinspection unchecked
        List<SysPrivilegeEntity> privs = list(
                new LambdaQueryWrapper<SysPrivilegeEntity>()
                        .orderByAsc(SysPrivilegeEntity::getParentId, SysPrivilegeEntity::getSort));
        // 根节点默认为0，即第一层权限的pid为0
        return getChildren(privs, "0");
    }

    /**
     * 递归查询privId的所有子权限
     *
     * @param privs  全权限列表
     * @param privId 当前权限id
     * @return 子权限列表
     */
    private List<PrivTreeDTO> getChildren(List<SysPrivilegeEntity> privs, String privId) {
        // 筛选出privId权限的子权限
        List<SysPrivilegeEntity> childPrivs = privs.stream()
                .filter(i -> i.getParentId().equals(privId)).collect(Collectors.toList());
        // 转vo
        List<PrivTreeDTO> children = childPrivs.stream()
                .map(i -> BeanUtil.copyProperties(i, PrivTreeDTO.class)).collect(Collectors.toList());
        // 退出递归条件，无子权限
        if (children.isEmpty()) {
            return null;
        }
        // 遍历当前所有子权限
        for (PrivTreeDTO i : children) {
            // 开始递归查询
            i.setChildren(getChildren(privs, i.getId()));
        }
        return children;
    }

    @Override
    public Page<SysPrivilegeEntity> getPrivilegeList(ListPrivilegeQuery query) {
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysPrivilegeEntity>()
                        .like(StrUtil.isNotBlank(query.getPrivName()),
                                SysPrivilegeEntity::getPrivName, query.getPrivName())
                        .orderByDesc(SysPrivilegeEntity::getCreateTime)
        );
    }

    @Override
    public String createPrivilege(PrivilegeQuery privilegeQuery) {
        // 新增权限
        SysPrivilegeEntity sysPrivilegeEntity = BeanUtil.copyProperties(privilegeQuery, SysPrivilegeEntity.class);
        return save(sysPrivilegeEntity) ? sysPrivilegeEntity.getId() : null;
    }

    @Override
    public PrivilegeVO getPrivilegeById(String privId) {
        // 查询权限表
        SysPrivilegeEntity sysPrivilegeEntity = getById(privId);
        if (sysPrivilegeEntity == null) {
            return null;
        }
        return BeanUtil.copyProperties(sysPrivilegeEntity, PrivilegeVO.class);
    }

    @Override
    public void updatePrivilegeById(UpdatePrivilegeQuery query) {
        // 更新权限表
        SysPrivilegeEntity sysPrivilegeEntity = BeanUtil.copyProperties(query, SysPrivilegeEntity.class);
        updateById(sysPrivilegeEntity);
    }

    @Override
    public void removePrivilegeById(String privId) {
        // 删除id的权限
        removeById(privId);
        // todo 删除子权限。这里只用了parentId来关联树形，并没有采用线索类字段，一并删除子节点较为麻烦
    }

    @Override
    public void deletePrivilegeBatch(List<String> privIds) {
        removeByIds(privIds);
    }
}
