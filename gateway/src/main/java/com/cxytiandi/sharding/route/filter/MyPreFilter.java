package com.cxytiandi.sharding.route.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/21
 * @Version 1.0.0
 */
public class MyPreFilter implements GatewayFilter, Ordered {
    private final Logger log = LoggerFactory.getLogger(MyPreFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MultiValueMap<String, HttpCookie> cookies=exchange.getRequest().getCookies();
        for (String k:cookies.keySet()){
            log.info("{}->{}",k,cookies.get(k));
        }
        log.info("限流处理");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
