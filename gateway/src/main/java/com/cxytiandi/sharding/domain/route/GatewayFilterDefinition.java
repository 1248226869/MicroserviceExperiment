package com.cxytiandi.sharding.domain.route;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由过滤器模型
 *
 * @author wangfengwf@chaoxing.cm
 * @since 2019-07-29 14:07
 */
@Data
public class GatewayFilterDefinition {

    /**
     * filter name
     */
    private String name;

    private Map<String,String> args = new LinkedHashMap<>();
}
