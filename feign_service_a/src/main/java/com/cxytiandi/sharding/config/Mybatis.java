package com.cxytiandi.sharding.config;

import com.cxytiandi.sharding.po.User;
import com.cxytiandi.sharding.repository.UserRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

import java.io.InputStream;
import java.util.List;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/22
 * @Version 1.0.0
 */
public class Mybatis {
    public static void main(String[] args) throws IOException {
        Configuration configuration=new Configuration();
        InputStream inputStream = Resources.getResourceAsStream("/Users/zhaotailin/Downloads/MicroserviceExperiment/feign_service_a/src/main/resources/META-INF/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = factory.openSession();
        //这里不再调用SqlSession 的api，而是获得了接口对象，调用接口中的方法。
        UserRepository mapper=sqlSession.getMapper(UserRepository.class);
        List<User> list=mapper.list();
        System.out.println(list);
    }

}
