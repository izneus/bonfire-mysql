package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.CommonUtil;
import com.izneus.bonfire.config.BonfireConfig;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.query.ListFileQuery;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.izneus.bonfire.module.system.mapper.SysFileMapper;
import com.izneus.bonfire.module.system.service.SysFileService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.izneus.bonfire.common.constant.Constant.TEMP_FILE;
import static com.izneus.bonfire.common.constant.Constant.UPLOAD_FILE;

/**
 * <p>
 * 上传文件信息表 服务实现类
 * </p>
 *
 * @author Izneus
 * @since 2020-09-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFileEntity> implements SysFileService {

    private final BonfireConfig bonfireConfig;
    private final JwtUtil jwtUtil;

    @Override
    public Page<SysFileEntity> listFiles(ListFileQuery query) {
        boolean isTimes = query.getCreateTimes() != null && query.getCreateTimes().size() == 2;
        return page(
                new Page<>(query.getPageNum(), query.getPageSize()),
                new LambdaQueryWrapper<SysFileEntity>()
                        .between(isTimes, SysFileEntity::getCreateTime,
                                query.getCreateTimes().get(0), query.getCreateTimes().get(1))
                        .and(wrapper -> wrapper
                                .like(StrUtil.isNotBlank(query.getQuery()),
                                        SysFileEntity::getFilename, query.getQuery())
                                .or()
                                .like(StrUtil.isNotBlank(query.getQuery()),
                                        SysFileEntity::getRemark, query.getQuery()))
        );
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        // 校验文件名
        if (multipartFile.isEmpty()) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "文件不能为空");
        }
        String filename = multipartFile.getOriginalFilename();
        if (!CommonUtil.isValidFilename(filename)) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "非法的文件名");
        }
        // 文件大小，单位字节
        Long sizeBytes = multipartFile.getSize();
        // 后缀名，无.句号
        String suffix = FileUtil.getSuffix(filename);
        String uniqueFilename = IdUtil.fastSimpleUUID() + "." + suffix;
        // 创建文件
        File file = FileUtil.touch(bonfireConfig.getPath().getUploadPath() + File.separator
                + uniqueFilename);
        // 转储文件
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("IOException", e);
            throw new BadRequestException(ErrorCode.INTERNAL, "转储文件失败", e);
        }
        // 保存记录到文件表
        SysFileEntity fileEntity = new SysFileEntity();
        fileEntity.setFilename(filename);
        fileEntity.setUniqueFilename(uniqueFilename);
        fileEntity.setSuffix(suffix);
        fileEntity.setPath(File.separator + uniqueFilename);
        fileEntity.setFileSize(sizeBytes);
        save(fileEntity);
        return fileEntity.getId();
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String token) {
        // 校验token有效性
        Claims claims = jwtUtil.getClaims(token);
        String filename = (String) claims.get("filename");
        String fileType = (String) claims.get("fileType");
        if (!CommonUtil.isValidFilename(filename)) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "非法的文件名");
        }
        // todo 可以考虑增加允许下载的文件后缀白名单
        // getBasePath，根据文件类型获得不同的根路径
        String basePath;
        switch (fileType) {
            case TEMP_FILE:
                basePath = bonfireConfig.getPath().getTempPath();
                break;
            case UPLOAD_FILE:
                basePath = bonfireConfig.getPath().getUploadPath();
                break;
            default:
                basePath = null;
                break;
        }
        if (StrUtil.isBlank(basePath)) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "非法的文件类型");
        }
        String filePath = basePath + File.separator + filename;
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
