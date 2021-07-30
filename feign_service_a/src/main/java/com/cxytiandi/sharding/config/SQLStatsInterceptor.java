package com.cxytiandi.sharding.config;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/19
 * @Version 1.0.0
 */

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;


@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class}) })
public class SQLStatsInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 它将直接覆盖你所拦截的对象，有个参数Invocation对象，通过该对象，可以反射调度原来对象的方法
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        logger.info("mybatis intercept sql:{}", sql);
        return invocation.proceed();
    }

    /**
     * target是被拦截的对象，它的作用是给被拦截对象生成一个代理对象；
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        logger.info("target is {}",target.toString());
        return Plugin.wrap(target, this);
    }

    /**
     * 允许在plugin元素中配置所需参数，该方法在插件初始化的时候会被调用一次；
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        String dialect = properties.getProperty("dialect");
        logger.info("mybatis intercept dialect:{}", dialect);
    }

}

