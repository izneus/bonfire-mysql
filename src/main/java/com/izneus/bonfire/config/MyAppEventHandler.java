package com.izneus.bonfire.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * ApplicationEvent监听处理类
 *
 * @author Izneus
 * @date 2022/01/06
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MyAppEventHandler {

    private final Environment env;

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEventHandler(ApplicationReadyEvent event) {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            String port = event.getApplicationContext().getEnvironment().getProperty("local.server.port");
            String isSsl = env.getProperty("server.ssl.enabled");
            String httpStr = "true".equals(isSsl) ? "https" : "http";
            log.info("Swagger地址：{}://{}:{}/swagger-ui/index.html", httpStr, hostAddress, port);
            log.info("Swagger地址：{}://localhost:{}/swagger-ui/index.html", httpStr, port);
        } catch (UnknownHostException e) {
            log.error("UnknownHostException", e);
        }
    }

}
