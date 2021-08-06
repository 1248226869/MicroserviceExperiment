package com.cxytiandi.sharding.config.cache;

import java.util.HashMap;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
public class CacheData {
    /**
     *
     * key
     *      f1 f2 f3 f4
     * data
     *
     */

    public static void main(String[] args) {
        HashMap<String, Integer> data=new HashMap<>();
        HashMap<String, String> keys=new HashMap<>();
        String id="id";
        String name="name";
        for (int i=0;i<100;i++){

            String f="f+"+i;
            String f1="f1+"+i;
            String f2="f1+"+i;
            String f3="f1+"+i;
            String f4="f1+"+i;
            keys.put(f,f);
            keys.put(f1,f);
            keys.put(f2,f);
            keys.put(f3,f);
            keys.put(f4,f);
            data.put(f,i);
        }

        for (int i=0;i<100;i++){

            String f="f+"+i;
            String f1="f1+"+i;
            String f2="f1+"+i;
            String f3="f1+"+i;
            String f4="f1+"+i;
            keys.get(f);
            keys.get(f1);
            keys.get(f2);
            keys.get(f3);
            keys.get(f4);
            System.out.println(data.get(keys.get(f))+" == "+data.get(keys.get(f1))+" == "+data.get(keys.get(f2))+" == "+data.get(keys.get(f3))+" == "+data.get(keys.get(f4)));
        }
    }

}
