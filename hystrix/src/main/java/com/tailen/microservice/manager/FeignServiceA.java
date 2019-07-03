package com.tailen.microservice.manager;

import com.tailen.microservice.manager.errorBack.FeignServiceAFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhao tailen
 * @description FeignServiceA
 * @date 2019-06-28
 */

@FeignClient(name = "feign-service-a",
        fallback = FeignServiceAFallback.class
        /*, configuration = FeignDisableHystrixConfiguration.class*/)
public interface FeignServiceA {
    @RequestMapping(value = "/api/add", method = RequestMethod.GET)
    Integer add(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB);
}
