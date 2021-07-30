package com.cxytiandi.sharding.route;

import com.cxytiandi.sharding.route.filter.MyPostFilter;
import com.cxytiandi.sharding.route.filter.MyPreFilter;
import com.cxytiandi.sharding.route.predicate.UserRoutePredicateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/21
 * @Version 1.0.0
 */
@Configuration
public class ConfigRouter {
    @Autowired
    private UserRoutePredicateFactory userRoutePredicateFactory;
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.route("mobilelearn_widgetstat_mutipart",
//                        r -> r.host("mobilelearn-stat.**")
//                                .and().method(HttpMethod.POST)
//                                .and().header(HttpHeaders.CONTENT_TYPE, multipartRegx)
//                                .and().path("/widget/**", "/v2/**")
//                                .uri("lb://ketang-widgetstat")
//                                .order(302)
//                )

        UserRoutePredicateFactory.Config config=new UserRoutePredicateFactory.Config();
        config.setFlag(11000L);
        return builder.routes()
                .route("feign-service-a",
                        r -> r.path("/api/*") .and().asyncPredicate(userRoutePredicateFactory.applyAsync(config))
                                .filters(
                                        f -> f.filter(new MyPreFilter())
                                                .filter(new MyPostFilter())
                                )
                                .uri("lb://feign-service-a")


                )
                .build();
    }
}
