package com.izneus.bonfire.module.system.controller.v1;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.CommonUtil;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.query.DownloadFileQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ExportFileQuery;
import com.izneus.bonfire.module.system.controller.v1.query.FileQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListFileQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.GetFileVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListFileVO;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.izneus.bonfire.module.system.service.SysFileService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
@RequestMapping("/api/v1")
public class SysFileController {

    @Value("${bonfire.tempPath}")
    private String tempPath;

    private final SysFileService fileService;
    private final JwtUtil jwtUtil;

    @AccessLog("上传文件")
    @ApiOperation("上传文件")
    @PostMapping("/files")
    @PreAuthorize("hasAuthority('sys:files:create')")
    public IdVO uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String fileId = fileService.uploadFile(multipartFile);
        return new IdVO(fileId);
    }

    @AccessLog("文件列表")
    @ApiOperation("文件列表")
    @GetMapping("/files")
    @PreAuthorize("hasAuthority('sys:files:list')")
    public ListFileVO listFiles(ListFileQuery query) {
        // todo 文件上传可能会有权限控制，比如admin看所有，user看自己上传的
        // 检查是否包含起止时间
        boolean isTimes = query.getCreateTimes() != null && query.getCreateTimes().size() == 2;
        Page<SysFileEntity> page = fileService.page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysFileEntity>()
                        .between(isTimes, SysFileEntity::getCreateTime,
                                query.getCreateTimes().get(0), query.getCreateTimes().get(1))
                        .and(wrapper -> wrapper
                                .like(StringUtils.hasText(query.getQuery()),
                                        SysFileEntity::getFilename, query.getQuery())
                                .or()
                                .like(StringUtils.hasText(query.getQuery()),
                                        SysFileEntity::getRemark, query.getQuery()))
        );
        return new ListFileVO(page);
    }

    @AccessLog("文件详情")
    @ApiOperation("文件详情")
    @GetMapping("/files/{id}")
    @PreAuthorize("hasAuthority('sys:files:get')")
    public GetFileVO getUserById(@NotBlank @PathVariable String id) {
        // todo 文件上传可能会有权限控制，比如admin看所有，user看自己上传的
        SysFileEntity fileEntity = fileService.getById(id);
        GetFileVO fileVO = new GetFileVO();
        BeanUtils.copyProperties(fileEntity, fileVO);
        return fileVO;
    }

    @AccessLog("更新文件")
    @ApiOperation("更新文件")
    @PutMapping("/files/{id}")
    @PreAuthorize("hasAuthority('sys:files:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthorityById(@NotBlank @PathVariable String id, @Validated @RequestBody FileQuery query) {
        // todo 这里只是简单的更新文件表中的记录，并不修改硬盘上保存的文件
        SysFileEntity fileEntity = new SysFileEntity();
        BeanUtils.copyProperties(query, fileEntity);
        fileEntity.setId(id);
        fileService.updateById(fileEntity);
    }

    @AccessLog("删除文件")
    @ApiOperation("删除文件")
    @DeleteMapping("/files/{id}")
    @PreAuthorize("hasAuthority('sys:files:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthorityById(@NotBlank @PathVariable String id) {
        fileService.removeById(id);
        // todo 有需要也可以继续删除硬盘上的文件
    }

    /**
     * 下载文件统一都是先返回jwt和文件名
     */
    @AccessLog("请求下载文件")
    @ApiOperation("请求下载文件")
    @PostMapping("/files:download")
    public void downloadFile(DownloadFileQuery query) {
        // todo 返回jwt 和 文件id 或者文件名字
    }

    @AccessLog("下载文件资源")
    @ApiOperation("下载文件资源")
    @GetMapping("/resources:download")
    public void downloadRes() {
        // todo 返回jwt 和 文件id 或者文件名字
    }

    @AccessLog("下载临时文件")
    @ApiOperation("下载临时文件")
    @GetMapping("/files:export")
    public ResponseEntity<Resource> exportFile(ExportFileQuery query) {
        // 校验token有效性
        // todo 等待测试jwt时效性
        Claims claims = jwtUtil.getClaims(query.getToken());
        String filename = (String) claims.get("filename");
        if (!query.getFilename().equals(filename)) {
            return ResponseEntity.notFound().build();
        }

        if (!CommonUtil.isValidFilename(query.getFilename())) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "非法文件名");
        }
        // todo 可以考虑增加允许下载的文件后缀白名单
        String filePath = tempPath + File.separator + query.getFilename();
        Path path = Paths.get(filePath);
        try {
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

}
