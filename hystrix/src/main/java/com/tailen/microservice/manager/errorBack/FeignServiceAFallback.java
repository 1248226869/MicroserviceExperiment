package com.tailen.microservice.manager.errorBack;

import com.tailen.microservice.controller.HystrixControllor;
import com.tailen.microservice.manager.FeignServiceA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */
@Component
public class FeignServiceAFallback implements FeignServiceA {
    private final Logger log = LoggerFactory.getLogger(HystrixControllor.class);

    @Override
    public Integer add(Integer numA, Integer numB) {
        log.info("FeignServiceA降级服务 addFallback");
        return numA - numB;
    }
}
