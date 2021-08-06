package com.cxytiandi.sharding.route.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/21
 * @Version 1.0.0
 */
public class MyPostFilter implements GatewayFilter, Ordered {

    private final Logger log=LoggerFactory.getLogger(MyPostFilter.class);
    private static final String REQUEST_TIME_BEGIN="requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime=exchange.getAttribute(REQUEST_TIME_BEGIN);
                    if (startTime != null) {
                        Long times=System.currentTimeMillis() - startTime;
                        log.info("{}:耗时 {} ms", exchange.getRequest().getURI().getRawPath(), times);
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
