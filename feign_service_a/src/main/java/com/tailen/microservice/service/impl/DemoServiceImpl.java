package com.tailen.microservice.service.impl;

import com.tailen.microservice.FeignServiceAApplication;
import com.tailen.microservice.manager.FeignServiceB;
import com.tailen.microservice.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private FeignServiceB feignServiceB;

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
