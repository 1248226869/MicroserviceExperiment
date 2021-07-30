package com.cxytiandi.sharding;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhao tailen
 * @description feign服务A
 * @date 2019-06-28
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignServiceAApplication {

    public static void main(String[] args) {
        new SpringApplication(FeignServiceAApplication.class).run(args);
    }
}
