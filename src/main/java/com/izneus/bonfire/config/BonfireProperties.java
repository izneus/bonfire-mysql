package com.izneus.bonfire.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Izneus
 * @date 2020/07/25
 */
@Configuration
@ConfigurationProperties(prefix = "bonfire")
@Data
public class BonfireProperties {
    private boolean captchaEnabled;
}
