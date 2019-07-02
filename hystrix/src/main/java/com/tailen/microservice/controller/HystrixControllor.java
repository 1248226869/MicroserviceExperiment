package com.tailen.microservice.controller;

import com.tailen.microservice.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */
@RestController
@RequestMapping(value = "/api/hystrix")
@Api("hystrix")
public class HystrixControllor {

    private final Logger log = LoggerFactory.getLogger(HystrixControllor.class);
    @Autowired
    private DemoService demoService;

    @ApiOperation(value = "加法")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("hystrix service : add param check fail");
            return null;
        }
        return demoService.add(numA, numB);
    }

    @ApiOperation(value = "乘法")
    @RequestMapping(value = "/multiplication", method = RequestMethod.GET)
    public Integer multiplication(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("hystrix service: multiplication param check fail");
            return null;
        }
        return demoService.multiplication(numA, numB);
    }


}
