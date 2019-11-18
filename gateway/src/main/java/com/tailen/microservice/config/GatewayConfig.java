package com.tailen.microservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhao tailen
 * @description 网关配置
 * @date 2019-07-15
 */
//@Configuration
public class GatewayConfig {



    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/gateway")
                        .uri("https://cloud.tencent.com/developer/ask/216213")
                ).build();
    }
}
