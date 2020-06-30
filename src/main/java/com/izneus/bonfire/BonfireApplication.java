package com.izneus.bonfire;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Izneus
 * @date 2020/06/30
 */
@SpringBootApplication
@MapperScan({"com.izneus.bonfire.module.**.mapper"})
public class BonfireApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonfireApplication.class, args);
    }

}
