package com.cxytiandi.sharding.test;

import com.cxytiandi.sharding.config.cache.local.ObjFieldCache;
import com.cxytiandi.sharding.po.User;

import java.lang.reflect.Field;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
public class Testf {
    public static void main(String[] args) throws IllegalAccessException {
        User user=new User();
        user.setCity("1");
        user.setName("name");
        user.setFlag(2);
        Class<?> aClass1=user.getClass();
        Field[] fields=aClass1.getDeclaredFields();
        for (Field f:fields){
            ObjFieldCache annotation=f.getAnnotation(ObjFieldCache.class);
            f.setAccessible(true);
            System.out.println(f.getName()+":"+annotation+":"+f.get(user));

        }
    }
}
