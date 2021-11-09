package com.izneus.bonfire.module.system.controller.v1;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.common.base.BasePageVO;
import com.izneus.bonfire.module.system.controller.v1.query.FileQuery;
import com.izneus.bonfire.module.system.controller.v1.query.ListFileQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.FileVO;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.controller.v1.vo.ListFileVO;
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

import javax.validation.constraints.NotBlank;
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

    @AccessLog("文件列表")
    @ApiOperation("文件列表")
    @GetMapping("/files")
    @PreAuthorize("hasAuthority('sys:files:list')")
    public BasePageVO<ListFileVO> listFiles(@Validated ListFileQuery query) {
        // todo 文件上传可能会有权限控制，比如admin看所有，user看自己上传的
        // todo validated list 参数
        // 检查是否包含起止时间
        Page<SysFileEntity> page = fileService.listFiles(query);
        List<ListFileVO> rows = page.getRecords().stream()
                .map(file -> BeanUtil.copyProperties(file, ListFileVO.class))
                .collect(Collectors.toList());
        return new BasePageVO<>(page, rows);
    }

    @AccessLog("文件详情")
    @ApiOperation("文件详情")
    @GetMapping("/files/{id}")
    @PreAuthorize("hasAuthority('sys:files:get')")
    public FileVO getUserById(@NotBlank @PathVariable String id) {
        // todo 文件上传可能会有权限控制，比如admin看所有，user看自己上传的
        SysFileEntity fileEntity = fileService.getById(id);
        return BeanUtil.copyProperties(fileEntity, FileVO.class);
    }

    @AccessLog("更新文件")
    @ApiOperation("更新文件")
    @PutMapping("/files/{id}")
    @PreAuthorize("hasAuthority('sys:files:update')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthorityById(@NotBlank @PathVariable String id, @Validated @RequestBody FileQuery query) {
        // todo 这里只是简单的更新文件表中的记录，并不修改硬盘上保存的文件
        SysFileEntity fileEntity = BeanUtil.copyProperties(query, SysFileEntity.class);
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

    @AccessLog("上传文件")
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('sys:file:upload') or hasAuthority('admin')")
    public IdVO uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String fileId = fileService.uploadFile(multipartFile);
        return new IdVO(fileId);
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

}
