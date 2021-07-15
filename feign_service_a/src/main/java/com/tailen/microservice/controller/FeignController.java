package com.tailen.microservice.controller;

import com.tailen.microservice.FeignServiceAApplication;
import com.tailen.microservice.manager.FeignServiceB;
import com.tailen.microservice.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-28
 */
@RestController
@RequestMapping(value = "/api")
@Api(tags="fegin service a")
public class FeignController {
    private final Logger log = LoggerFactory.getLogger(FeignServiceAApplication.class);
    @Autowired
    private DemoService demoService;
    @Autowired
    private FeignServiceB feignServiceB;
    @Autowired
    public DiscoveryClient discoveryClient;
    @ApiOperation(value="加法")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service a : add param check fail");
            return null;
        }
        log.info("fegin service a provide service:add");
        return demoService.add(numA, numB);
    }

    @ApiOperation(value="feign调用B服务乘法")
    @RequestMapping(value = "/multiplication", method = RequestMethod.GET)
    public Integer multiplication(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service a : multiplication param check fail");
            return null;
        }
        log.info("fegin service a remote use fegin service b ");
        return feignServiceB.multiplication(numA, numB);
    }
}
