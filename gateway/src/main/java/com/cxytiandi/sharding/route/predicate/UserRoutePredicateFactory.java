package com.cxytiandi.sharding.route.predicate;

import com.cxytiandi.sharding.route.filter.MyPostFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;


/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/21
 * @Version 1.0.0
 */
@Component
@Order(1)
public class UserRoutePredicateFactory extends AbstractRoutePredicateFactory<UserRoutePredicateFactory.Config> {
    private final Logger log=LoggerFactory.getLogger(MyPostFilter.class);

    public UserRoutePredicateFactory() {
        super(UserRoutePredicateFactory.Config.class);
    }

    @Override
    public ShortcutType shortcutType() {
        return ShortcutType.GATHER_LIST;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("sources");
    }

    @Override
    public Predicate<ServerWebExchange> apply(UserRoutePredicateFactory.Config config) {
        return exchange -> {
            URI uri=exchange.getRequest().getURI();
            String path=uri.getPath();
            //限流、降级
            MultiValueMap<String, HttpCookie> cookies=exchange.getRequest().getCookies();
            log.info("================cookies: {}", cookies);

            return true;
        };
    }

    @Validated
    public static class Config {
        private long flag;

        public long getFlag() {
            return flag;
        }

        public void setFlag(long flag) {
            this.flag=flag;
        }
    }
}

