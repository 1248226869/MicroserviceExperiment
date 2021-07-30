package com.cxytiandi.sharding.config;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/19
 * @Version 1.0.0
 */
@Intercepts({@Signature(type=StatementHandler.class, method="prepare", args={Connection.class, Integer.class})})
public class ShardStrategyConfig implements Interceptor {
    private static final ReflectorFactory defaultReflectorFactory=new DefaultReflectorFactory();

    private final Logger log=LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("Interceptor ...");
        StatementHandler statementHandler=(StatementHandler) invocation.getTarget();
        MetaObject metaObject=MetaObject.forObject(statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                defaultReflectorFactory
        );

        MappedStatement mappedStatement=(MappedStatement)
                metaObject.getValue("delegate.mappedStatement");

        String id=mappedStatement.getId();
        id=id.substring(0, id.lastIndexOf('.'));
        Class clazz=Class.forName(id);

        // 获取TableShard注解
        TableShard tableShard=(TableShard) clazz.getAnnotation(TableShard.class);
        if (tableShard != null) {
            String field=tableShard.field();
            String tableName=tableShard.tableName();
            System.out.println("field is " + field);
            Class<? extends ITableShardStrategy> strategyClazz=tableShard.shardStrategy();
            ITableShardStrategy strategy=strategyClazz.newInstance();
            // 获取源sql
            String newTableName=tableName;
            String sql=(String) metaObject.getValue("delegate.boundSql.sql");
            ArrayList<ParameterMapping> originalObject=(ArrayList<ParameterMapping>) metaObject.getValue("delegate.boundSql.parameterMappings");
            metaObject.getValue("delegate.boundSql.parameterObject");
            for (ParameterMapping p : originalObject) {
                String property=p.getProperty();
                if (field.equals(property)) {
                    newTableName=strategy.tableShard(tableName, 0L);
                    break;
                }

            }
            System.out.println("sql is " + sql);
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
