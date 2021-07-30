package com.cxytiandi.sharding.service.impl;

import com.cxytiandi.sharding.manager.FeignServiceA;
import com.cxytiandi.sharding.manager.FeignServiceB;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.cxytiandi.sharding.controller.HystrixControllor;
import com.cxytiandi.sharding.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */
@Service
public class DemoServiceImpl implements DemoService {
    private final Logger log = LoggerFactory.getLogger(HystrixControllor.class);

    @Autowired
    private FeignServiceA feignServiceA;
    @Autowired
    private FeignServiceB feignServiceB;


    @Override
    public Integer add(Integer numA, Integer numB) {
        Integer add = feignServiceA.add(numA, numB);
        log.info("正常服务 add");
        return add;
    }



    @HystrixCommand(fallbackMethod = "multiplicationFallback")
    @Override
    public Integer multiplication(Integer numA, Integer numB) {
        Integer multiplication = feignServiceB.multiplication(numA, numB);
        log.info("正常服务 multiplication");
        return multiplication;
    }

    public Integer multiplicationFallback(Integer numA, Integer numB) {
        log.info("降级服务 multiplicationFallback");
        return -2;
    }


}
