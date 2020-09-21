package com.izneus.bonfire.module.system.service;

import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 上传文件信息表 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-09-15
 */
public interface SysFileService extends IService<SysFileEntity> {

    String uploadFile(MultipartFile multipartFile);

}
