package com.tailen.microservice.eureka;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhao tailen
 * @description 服务注册中心
 * @date 2019-06-28
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaApplication.class)
                .web(true).run(args);
    }
}
