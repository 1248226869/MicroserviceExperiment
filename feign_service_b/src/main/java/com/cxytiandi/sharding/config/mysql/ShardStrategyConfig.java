package com.cxytiandi.sharding.config.mysql;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/19
 * @Version 1.0.0
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class ShardStrategyConfig implements Interceptor {
    private static final ReflectorFactory defaultReflectorFactory = new DefaultReflectorFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("111111111111");
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                defaultReflectorFactory
        );
        MappedStatement mappedStatement = (MappedStatement)
                metaObject.getValue("delegate.mappedStatement");

        String id = mappedStatement.getId();
        id = id.substring(0, id.lastIndexOf('.'));
        Class clazz = Class.forName(id);

        // 获取TableShard注解
        TableShard tableShard = (TableShard)clazz.getAnnotation(TableShard.class);
        if ( tableShard != null ) {
            String tableName = tableShard.tableName();
            System.out.println("tableName is "+tableName);
            Class<? extends ITableShardStrategy> strategyClazz = tableShard.shardStrategy();
            ITableShardStrategy strategy = strategyClazz.newInstance();
            String newTableName = strategy.fieldShard(tableName);
            // 获取源sql
            String sql = (String)metaObject.getValue("delegate.boundSql.sql");
            System.out.println("sql is "+sql);
            // 用新sql代替旧sql, 完成所谓的sql rewrite
            metaObject.setValue("delegate.boundSql.sql", sql.replaceAll(tableName, newTableName));
        }

        // 传递给下一个拦截器处理
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身, 减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
