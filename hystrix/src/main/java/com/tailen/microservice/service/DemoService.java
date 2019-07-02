package com.tailen.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */

public interface DemoService {
    @HystrixCommand(fallbackMethod = "addFallback")
    Integer add(Integer numA, Integer numB);

    @HystrixCommand(fallbackMethod = "multiplicationFallback")
    Integer multiplication(Integer numA, Integer numB);
}
