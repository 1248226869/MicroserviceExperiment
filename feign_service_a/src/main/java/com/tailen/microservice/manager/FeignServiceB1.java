package com.tailen.microservice.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhao tailen
 * @description FeignServiceB
 * @date 2019-06-28
 */

@FeignClient("feign-service-b1")
public interface FeignServiceB1 {
    @RequestMapping(value = "/api/multiplication", method = RequestMethod.GET)
    Integer multiplication(@RequestParam(value="numA") Integer numA, @RequestParam(value="numB") Integer numB);
}
