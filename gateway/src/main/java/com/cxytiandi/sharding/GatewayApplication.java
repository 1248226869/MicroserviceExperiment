package com.cxytiandi.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zhao tailen
 * @description Gateway
 * @date 2019-06-28
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
public class GatewayApplication {

    public static void main(String[] args) {
        new SpringApplication(GatewayApplication.class).run(args);
    }
}
