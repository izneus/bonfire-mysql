package com.izneus.bonfire.config;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * bonfire工程使用的一些自定义参数，具体含义可以参考.yml内注释
 *
 * @author Izneus
 * @date 2020/07/25
 */
@Configuration
@ConfigurationProperties(prefix = "bonfire")
@Data
public class BonfireProperties {

    @SuppressWarnings("WeakerAccess")
    @Data
    public static class BonfirePath {
        private String uploadPath;
        private String tempPath;
    }

    private Boolean captchaEnabled;
    private String defaultPassword;
    private BonfirePath mac;
    private BonfirePath win;
    private BonfirePath linux;

    public BonfirePath getPath() {
        OsInfo osInfo = SystemUtil.getOsInfo();
        if (osInfo.isWindows()) {
            return win;
        } else if (osInfo.isMac()) {
            return mac;
        } else {
            return linux;
        }
    }

}
