package com.izneus.bonfire.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉应用集合参数
 *
 * @author Izneus
 * @date 2022-12-15
 */
@Configuration
@ConfigurationProperties(prefix = "dingtalk")
@Data
public class DingTalkConfig {
    /**
     * 配置文件设置的bonfire应用参数
     */
    private DingTalkAppConfig appBonfire;

    /**
     * lamp应用参数
     */
    private DingTalkAppConfig appLamp;

    // 更多app参数可追加在下面
}
