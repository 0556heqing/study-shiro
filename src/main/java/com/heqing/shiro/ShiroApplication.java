package com.heqing.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author heqing
 * @date 2021/7/14 10:40
 */
@EnableConfigurationProperties
@SpringBootApplication
public class ShiroApplication {

    public static void main(String[] args) {
        new SpringApplication(ShiroApplication.class).run(args);
    }

}
