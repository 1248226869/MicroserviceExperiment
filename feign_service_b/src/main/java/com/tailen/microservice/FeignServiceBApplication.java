package com.tailen.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhao tailen
 * @description feign服务A
 * @date 2019-06-28
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
public class FeignServiceBApplication {

    public static void main(String[] args) {
        new SpringApplication(FeignServiceBApplication.class).run(args);
    }
}
