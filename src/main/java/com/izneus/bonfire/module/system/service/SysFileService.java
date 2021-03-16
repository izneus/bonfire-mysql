package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.ListFileQuery;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 上传文件信息表 服务类
 * </p>
 *
 * @author Izneus
 * @since 2020-09-15
 */
public interface SysFileService extends IService<SysFileEntity> {
    /**
     * 文件列表查询
     *
     * @param query 查询参数
     * @return 分页信息
     */
    Page<SysFileEntity> listFiles(ListFileQuery query);

    /**
     * 上传文件
     *
     * @param multipartFile 上传文件
     * @return 文件id
     */
    String uploadFile(MultipartFile multipartFile);

    /**
     * 下载文件
     *
     * @param token 文件下载2步中第一步生成的token
     * @return ResponseEntity<Resource>
     */
    ResponseEntity<Resource> downloadFile(String token);


}
