package com.izneus.bonfire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Izneus
 * @date 2020/06/30
 */
@SpringBootApplication
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class BonfireApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonfireApplication.class, args);
    }

}
