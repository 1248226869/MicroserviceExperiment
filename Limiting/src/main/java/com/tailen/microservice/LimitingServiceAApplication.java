package com.tailen.microservice;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-28
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class LimitingServiceAApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                LimitingServiceAApplication.class)
                .web(true).run(args);
    }
}
