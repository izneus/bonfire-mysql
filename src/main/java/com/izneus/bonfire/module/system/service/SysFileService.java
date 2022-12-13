package com.izneus.bonfire.module.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izneus.bonfire.module.system.controller.v1.query.UploadChunkQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ChunkQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListFileQuery;
import com.izneus.bonfire.module.system.entity.SysFileChunkEntity;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
     * 上传文件分片
     *
     * @param chunkFileQuery 分片文件信息，包含二进制文件
     * @return 分片id
     */
    String uploadChunk(UploadChunkQuery chunkFileQuery);

    /**
     * 检查文件分片
     *
     * @param chunkQuery 分片文件基础信息
     * @return 分片id
     */
    @SuppressWarnings("unused")
    String checkChunk(ChunkQuery chunkQuery);

    /**
     * 一次性检查文件分片，返回所有已经上传的分片序号
     *
     * @param chunkQuery 分片信息
     * @return 分片列表
     */
    List<SysFileChunkEntity> listUploadedChunks(ChunkQuery chunkQuery);

    /**
     * 上传多个文件
     *
     * @param multipartFiles 上传文件列表
     * @return 文件id
     */
    List<String> uploadFiles(List<MultipartFile> multipartFiles);

    /**
     * 下载文件第一步，获取临时token
     *
     * @param filename 文件名
     * @param fileType 文件类型
     * @return token
     */
    String getFileToken(String filename, String fileType);

    /**
     * 下载文件第二步
     *
     * @param token 文件下载临时token
     * @return ResponseEntity<Resource>
     */
    ResponseEntity<Resource> downloadFile(String token);

}
