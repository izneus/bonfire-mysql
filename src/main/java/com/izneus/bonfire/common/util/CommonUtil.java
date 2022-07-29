package com.izneus.bonfire.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.EnumUtil;
import com.izneus.bonfire.module.system.controller.v1.query.UploadChunkQuery;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

/**
 * 其他通用工具类
 *
 * @author Izneus
 * @date 2020-08-03
 */
@Slf4j
public class CommonUtil {

    private static final int MAX_FILENAME_LENGTH = 255;
    private static final String PARENT_DIR = "..";

    /**
     * 判断是否是合法的文件名
     *
     * @param filename 文件名
     * @return boolean
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isValidFilename(String filename) {
        if (filename == null || filename.length() > MAX_FILENAME_LENGTH) {
            return false;
        }
        // 是否含有相对路径，只是个简单的安全性检查
        if (filename.contains(PARENT_DIR)) {
            return false;
        }
        return !FileUtil.containsInvalid(filename);
    }

    /**
     * 通过枚举中自定义字段的值，获得枚举实例
     *
     * @param enumClass  枚举类
     * @param fieldName  字段名
     * @param fieldValue 字段值
     * @param <E>        枚举类
     * @return 枚举实例
     */
    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String fieldName, Object fieldValue) {
        // 先获得name:value的map，再互换key:value的位置，通过value获得name，然后获得枚举实例
        Map<String, Object> enumMap = EnumUtil.getNameFieldMap(enumClass, fieldName);
        Map<Object, String> inverseMap = MapUtil.inverse(enumMap);
        String enumName = MapUtil.getStr(inverseMap, fieldValue);
        return EnumUtil.fromString(enumClass, enumName);
    }

    /**
     * 生成加密密码
     *
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public static String encryptPassword(String plaintext) {
        return new BCryptPasswordEncoder().encode(plaintext);
    }

    public static List<Date> parseDateRange(List<Date> dateRange) {
        Date startTime = null;
        Date endTime = null;
        boolean hasTime = dateRange != null && dateRange.size() == 2;
        if (hasTime) {
            startTime = dateRange.get(0);
            endTime = dateRange.get(1);
        }
        return Arrays.asList(startTime, endTime);
    }

    /**
     * 创建分片文件保存路径
     *
     * @param basePath 根目录
     * @param chunk    分片信息
     * @return 路径
     */
    @SneakyThrows
    public static String generatePath(String basePath, UploadChunkQuery chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(basePath).append(File.separator).append(chunk.getIdentifier());
        // todo 多余代码，可以考虑精简
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            log.info("path not exist,create path: {}", sb.toString());
            Files.createDirectories(Paths.get(sb.toString()));
        }
        return sb.append(File.separator)
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }

    /**
     * 合并分片文件
     *
     * @param folder   目录
     * @param filename 文件名
     */
    @SneakyThrows
    public static void mergeChunks(String folder, String filename) {
        String targetFile = folder + File.separator + filename;

        if (FileUtil.exist(targetFile)) {
            // 存在文件，不用继续合并
            return;
        }
        Files.createFile(Paths.get(targetFile));
        Stream<Path> stream = Files.list(Paths.get(folder));
        stream
                .filter(path ->
                        !path.getFileName().toString().equals(filename)
                                && !".DS_Store".equals(path.getFileName().toString())
                )
                .sorted((o1, o2) -> {
                    String p1 = o1.getFileName().toString();
                    String p2 = o2.getFileName().toString();
                    int i1 = p1.lastIndexOf("-");
                    int i2 = p2.lastIndexOf("-");
                    return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                })
                .forEach(path -> {
                    try {
                        //以追加的形式写入文件
                        Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                        //合并后删除该块
                        Files.delete(path);
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                });
    }

}
