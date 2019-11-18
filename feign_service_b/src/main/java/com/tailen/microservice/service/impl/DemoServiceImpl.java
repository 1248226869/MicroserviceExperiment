package com.tailen.microservice.service.impl;

import com.tailen.microservice.FeignServiceBApplication;
import com.tailen.microservice.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author zhao tailen
 * @description DemoService的实现
 * @date 2019-06-28
 */
@Service
public class DemoServiceImpl implements DemoService {
    private final Logger log = LoggerFactory.getLogger(FeignServiceBApplication.class);

    @Override
    public Integer multiplication(Integer numA, Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service b : multiplication method param null");
            return null;
        }
        log.info("fegin service b : multiplication method numA is {} ;numB is {} ", numA, numB);
        return numA * numB;

    }

}
