package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.CommonUtils;
import com.izneus.bonfire.module.system.entity.SysFileEntity;
import com.izneus.bonfire.module.system.mapper.SysFileMapper;
import com.izneus.bonfire.module.system.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFileEntity> implements SysFileService {

    @Value("${bonfire.uploadBasePath}")
    private String uploadBasePath;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        // 校验文件名
        if (multipartFile.isEmpty()) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "文件不能为空");
        }
        String filename = multipartFile.getOriginalFilename();
        if (!CommonUtils.isValidFilename(filename)) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "非法的文件名");
        }
        // 文件大小，单位字节
        Long sizeBytes = multipartFile.getSize();
        // 后缀名，无.句号
        String suffix = FileUtil.getSuffix(filename);
        String uniqueFilename = IdUtil.fastSimpleUUID() + "." + suffix;
        // 创建文件
        File file = FileUtil.touch(uploadBasePath + File.separator + uniqueFilename);
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
}
