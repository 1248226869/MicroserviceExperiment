package com.tailen.microservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tailen.microservice.controller.HystrixControllor;
import com.tailen.microservice.manager.FeignServiceA;
import com.tailen.microservice.manager.FeignServiceB;
import com.tailen.microservice.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    @HystrixCommand(fallbackMethod = "addFallback")
    @Override
    public Integer add(Integer numA, Integer numB) {
        Integer add = feignServiceA.add(numA, numB);
        log.info("正常服务 add");
        return add;
    }

    public Integer addFallback() {
        log.info("降级服务 addFallback");
        return -1;
    }

    @HystrixCommand(fallbackMethod = "multiplicationFallback")
    @Override
    public Integer multiplication(Integer numA, Integer numB) {
        Integer multiplication = feignServiceB.multiplication(numA, numB);
        log.info("正常服务 multiplication");
        return multiplication;
    }

    public Integer multiplicationFallback() {
        log.info("降级服务 multiplicationFallback");
        return -2;
    }


}
