package com.izneus.bonfire.module.system.controller.v1;


import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.vo.IdVO;
import com.izneus.bonfire.module.system.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    private final SysFileService fileService;

    @AccessLog("上传文件")
    @ApiOperation("上传文件")
    @PostMapping("/files")
    @PreAuthorize("hasAuthority('sys:files:create')")
    public IdVO uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String fileId = fileService.uploadFile(multipartFile);
        return new IdVO(fileId);
    }
}
