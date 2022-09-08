package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.common.util.CommonUtil;
import com.izneus.bonfire.common.util.MinioUtil;
import com.izneus.bonfire.config.BonfireConfig;
import com.izneus.bonfire.module.system.controller.v1.query.*;
import com.izneus.bonfire.module.system.controller.v1.vo.*;
import com.izneus.bonfire.module.system.entity.SysFileChunkEntity;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.izneus.bonfire.module.system.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import oshi.util.FormatUtil;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 上传文件信息表 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-09-15
 */
@Api(tags = "系统:文件")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/file")
public class SysFileController {

    private final SysFileService fileService;
    // private final MinioUtil minioUtil;
    private final BonfireConfig bonfireConfig;

    @AccessLog("文件列表")
    @ApiOperation("文件列表")
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:file:list') or hasAuthority('admin')")
    public BasePageVO<ListFileVO> listFiles(@Validated @RequestBody ListFileQuery query) {
        // todo 文件上传可能会有权限控制，比如admin看所有，user看自己上传的
        Page<SysFileEntity> page = fileService.listFiles(query);
        List<ListFileVO> rows = page.getRecords().stream()
                .map(file -> {
                    ListFileVO vo = BeanUtil.copyProperties(file, ListFileVO.class);
                    vo.setFileSize(FormatUtil.formatBytes(file.getFileSize()));
                    return vo;
                })
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("文件信息详情")
    @ApiOperation("文件信息详情")
    @PostMapping("/get")
    @PreAuthorize("hasAuthority('sys:file:get') or hasAuthority('admin')")
    public FileVO getFileById(@Validated @RequestBody IdQuery query) {
        // todo 文件上传可能会有权限控制，比如admin看所有，user看自己上传的
        SysFileEntity fileEntity = fileService.getById(query.getId());
        return BeanUtil.copyProperties(fileEntity, FileVO.class);
    }

    @AccessLog("更新文件")
    @ApiOperation("更新文件")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:file:update') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFileById(@Validated @RequestBody UpdateFileQuery query) {
        // todo 这里只是简单的更新文件表中的记录，并不修改硬盘上保存的文件
        SysFileEntity fileEntity = BeanUtil.copyProperties(query, SysFileEntity.class);
        fileService.updateById(fileEntity);
    }

    @AccessLog("删除文件")
    @ApiOperation("删除文件")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:file:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFileById(@Validated @RequestBody IdQuery query) {
        fileService.removeById(query.getId());
        // todo 有需要也可以继续删除硬盘上的文件
    }

    @AccessLog("批量删除文件")
    @ApiOperation("批量删除文件")
    @PostMapping("/batchDelete")
    @PreAuthorize("hasAuthority('sys:file:delete') or hasAuthority('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFilesByIds(@Validated @RequestBody IdsQuery query) {
        fileService.removeByIds(query.getIds());
        // todo 有需要也可以继续删除硬盘上的文件
    }

    @AccessLog("上传文件")
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('sys:file:upload') or hasAuthority('admin')")
    public IdVO uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String fileId = fileService.uploadFile(multipartFile);
        return new IdVO(fileId);
    }

    @AccessLog("上传多文件")
    @ApiOperation("上传多文件")
    @PostMapping(value = "/batchUpload")
    @PreAuthorize("hasAuthority('sys:file:upload') or hasAuthority('admin')")
    public IdsVO uploadFiles(@RequestParam("file") List<MultipartFile> multipartFiles) {
        // 多文件接口swagger测试失败，content-type好像设置错了，postman测试可以
        List<String> fileIds = fileService.uploadFiles(multipartFiles);
        return new IdsVO(fileIds);
    }

    /**
     * 下载文件分为2步，第一步请求返回token，第二步用之前的token，get请求真正的文件
     */
    @AccessLog("下载文件")
    @ApiOperation("下载文件")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@NotBlank String token) {
        return fileService.downloadFile(token);
    }

    /**
     * 提供给oss使用预签名
     *
     * @return url
     */
    /// todo 添加minio相关功能
    /*@ApiOperation("生成上传路径")
    @PostMapping("/getUploadUrl")
    public String getUploadUrl() {
        return minioUtil.getUploadUrl("xxx.png");
    }*/

    @AccessLog("上传分片文件")
    @ApiOperation("上传分片文件")
    @PostMapping("/uploadChunk")
    @PreAuthorize("hasAuthority('sys:file:upload') or hasAuthority('admin')")
    public String uploadChunk(UploadChunkQuery chunk) {
        return fileService.uploadChunk(chunk);
    }

    @AccessLog("检查分片文件")
    @ApiOperation("检查分片文件")
    @GetMapping("/uploadChunk")
    @PreAuthorize("hasAuthority('sys:file:upload') or hasAuthority('admin')")
    public CheckChunkVO checkChunk(ChunkQuery chunk) {
        /// return fileService.checkChunk(chunk);
        List<SysFileChunkEntity> chunks = fileService.listUploadedChunks(chunk);
        List<Long> chunkNumbers = chunks.stream()
                .map(SysFileChunkEntity::getChunkNumber).collect(Collectors.toList());
        return CheckChunkVO.builder().uploadChunks(chunkNumbers).build();
    }

    @AccessLog("合并文件")
    @ApiOperation("合并文件")
    @PostMapping("/mergeChunks")
    @PreAuthorize("hasAuthority('sys:file:upload') or hasAuthority('admin')")
    public String mergeChunks(@RequestBody MergeFileQuery query) {
        String filename = query.getFilename();
        String folder = bonfireConfig.getPath().getUploadPath()
                + File.separator
                + query.getIdentifier();
        CommonUtil.mergeChunks(folder, filename);
        /// todo 保存新生成的文件，返回相应信息
        // fileInfo.setLocation(file);
        // fileInfoService.addFileInfo(fileInfo);
        return "合并成功";
    }

}
