package com.izneus.bonfire.common.util;

import cn.hutool.core.io.FileUtil;

/**
 * 其他通用工具类
 *
 * @author Izneus
 * @date 2020/08/03
 */
public class CommonUtils {

    private static final int MAX_FILENAME_LENGTH = 255;
    private static final String RELATIVE_PATH = "..";

    /**
     * 判断是否是合法的文件名
     *
     * @param filename 文件名
     * @return boolean
     */
    public static boolean isValidFilename(String filename) {
        if (filename == null || filename.length() > MAX_FILENAME_LENGTH) {
            return false;
        }
        // 是否含有相对路径，只是个简单的安全性检查
        if (filename.contains(RELATIVE_PATH)) {
            return false;
        }
        return !FileUtil.containsInvalid(filename);
    }

}
