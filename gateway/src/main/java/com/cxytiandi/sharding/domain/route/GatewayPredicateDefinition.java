package com.cxytiandi.sharding.domain.route;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言模型
 *
 * @author wangfengwf@chaoxing.cm
 * @since 2019-07-29 14:08
 */
@Data
public class GatewayPredicateDefinition {
    /**
     * 断言对应的name
     */
    private String Name;
    /**
     * 配置的断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
