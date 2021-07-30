package com.cxytiandi.sharding.controller;

import com.cxytiandi.sharding.FeignServiceBApplication;
import com.cxytiandi.sharding.manager.FeignServiceA;
import com.cxytiandi.sharding.service.DemoService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-28
 */
@RestController
@RequestMapping(value="/api")
@Api(tags="fegin service b")
public class FeignController {
    private final Logger log=LoggerFactory.getLogger(FeignServiceBApplication.class);
    @Autowired
    private DemoService demoService;
    @Autowired
    private FeignServiceA feignServiceA;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value="乘法")
    @RequestMapping(value="/multiplication", method=RequestMethod.GET)
    @HystrixCommand(fallbackMethod="multiplicationFallback")
    public Integer multiplication(@RequestParam(value="numA") Integer numA, @RequestParam(value="numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service b : multiplication param check fail");
            return null;
        }
        log.info("fegin service b provide service:multiplication");
        return demoService.multiplication(numA, numB);
    }

    //    @TailenHystrixCommand(fallback = "multiplicationFallback", timeout = 3000)
    @HystrixCommand(fallbackMethod="multiplicationFallback")
    @ApiOperation(value="feign调用A服务加法")
    @RequestMapping(value="/add", method=RequestMethod.GET)
    public Integer add(@RequestParam(value="numA") Integer numA, @RequestParam(value="numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service a : add param check fail");
            return null;
        }
        log.info("fegin service b remote use fegin service a ");
        String url="http://feign-service-a/api/add?numA={1}&numB={2}";
        ResponseEntity<Integer> responseEntity=restTemplate.getForEntity(url, Integer.class, numA, numB);
        return responseEntity.getBody();
    }


    public Integer multiplicationFallback(Integer numA, Integer numB) {
        log.info("降级服务 multiplicationFallback");
        return -2;
    }
}
