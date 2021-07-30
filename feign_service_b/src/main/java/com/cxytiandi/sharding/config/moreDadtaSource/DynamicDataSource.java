package com.cxytiandi.sharding.config.moreDadtaSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Description 该类继承自AbstractRoutingDataSource，
 *              需要实现determineCurrentLookupKey()方法，返回给spring一个代表数据源的key，
 *              通过该key，在AbstractRoutingDataSource的resolvedDataSources(Map<Object, DBSource>类型)中查找到对应的数据源datasource
 * @Author zhao tailin
 * @Date 2021/7/22
 * @Version 1.0.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    //根据DataSourceContextHolder中的值获取数据源的key
    @Override
    protected Object determineCurrentLookupKey() {
        //设置负载均衡
        return DataSourceContextHolder.getDataSource();
    }
}
