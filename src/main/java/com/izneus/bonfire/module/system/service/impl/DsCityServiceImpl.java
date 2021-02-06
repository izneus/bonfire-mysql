package com.izneus.bonfire.module.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.izneus.bonfire.module.system.entity.DsCityEntity;
import com.izneus.bonfire.module.system.mapper.DsCityMapper;
import com.izneus.bonfire.module.system.service.DsCityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 多数据源测试表 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2021-02-03
 */
@Service
@DS("lamp")
public class DsCityServiceImpl extends ServiceImpl<DsCityMapper, DsCityEntity> implements DsCityService {

}
