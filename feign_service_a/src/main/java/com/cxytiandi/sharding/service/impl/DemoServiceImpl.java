package com.cxytiandi.sharding.service.impl;

import com.cxytiandi.sharding.FeignServiceAApplication;
import com.cxytiandi.sharding.controller.FeignController;
import com.cxytiandi.sharding.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zhao tailen
 * @description DemoService的实现
 * @date 2019-06-28
 */
@Service
public class DemoServiceImpl implements DemoService {
    private final Logger log = LoggerFactory.getLogger(FeignServiceAApplication.class);
    @Autowired
    private FeignController feignController;
    @Override
    public Integer add(Integer numA, Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service a : add method param null");
            return null;
        }
        log.info("fegin service a : add method numA is {} ;numB is {} ", numA, numB);
//        int a =0 ;
//        int b = a/a;
        return numA + numB;

    }

}