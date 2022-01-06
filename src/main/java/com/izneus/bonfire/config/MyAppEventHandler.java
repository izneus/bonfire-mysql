package com.izneus.bonfire.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyEventHandler(ApplicationReadyEvent event) {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            String port = event.getApplicationContext().getEnvironment().getProperty("local.server.port");
            log.info("Swagger地址：http://{}:{}/swagger-ui.html", hostAddress, port);
            log.info("Swagger地址：http://localhost:{}/swagger-ui.html", port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
