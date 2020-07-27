package com.izneus.bonfire.config;

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
    private Boolean captchaEnabled;
    private String defaultPassword;
}
