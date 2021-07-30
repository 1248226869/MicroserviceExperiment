package com.cxytiandi.sharding.domain.route;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由模型
 *
 * @author wangfengwf@chaoxing.cm
 * @since 2019-07-29 14:01
 */
@Data
public class GatewayRouteDefinition {
    /**
     * 路由id
     */
    private String id;

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * 路由执行顺序
     */
    private int order  = 0;

    /**
     * 路由断言集合
     */
    private List<GatewayPredicateDefinition> predicates = new ArrayList<>();

    /**
     * 路由过滤器集合
     */
    private List<GatewayFilterDefinition> filters = new ArrayList<>();
}
