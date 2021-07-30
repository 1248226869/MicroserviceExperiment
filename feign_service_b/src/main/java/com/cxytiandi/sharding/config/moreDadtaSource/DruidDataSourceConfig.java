package com.cxytiandi.sharding.config.moreDadtaSource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/22
 * @Version 1.0.0
 */
@Configuration
public class DruidDataSourceConfig {
    //配置oracle、mysql数据源
    @Bean("mysql")
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSource dataSource1(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean("oracle")
    @ConfigurationProperties("spring.datasource.oracle")
    public DataSource dataSource2(){
        return DruidDataSourceBuilder.create().build();
    }

    @Primary //当相同类型的实现类存在时，选择该注解标记的类
    @Bean("dynamicDS")
    public DataSource dataSource(){
        DynamicDataSource dds=new DynamicDataSource();
        //默认数据源，mysql
        dds.setDefaultTargetDataSource(dataSource1());
        Map<Object,Object> dsMap=new HashMap<>();
        dsMap.put(DataSourceType.MYSQL,dataSource1());
        dsMap.put(DataSourceType.ORACLE,dataSource2());
        dds.setTargetDataSources(dsMap);
        return dds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}
