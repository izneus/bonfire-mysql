package com.izneus.bonfire.module.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Izneus
 * @date 2020/07/01
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secret;
    private Long expire;
    private String header;
    private String type;
}
