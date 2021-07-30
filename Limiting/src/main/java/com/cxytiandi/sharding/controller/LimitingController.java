package com.cxytiandi.sharding.controller;

import com.cxytiandi.sharding.config.limiting.Limiting;
import com.cxytiandi.sharding.config.limiting.LimitingConfig;
import com.cxytiandi.sharding.manager.FeignServiceA;
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
 * @date 2019-07-03
 */
@RestController
@RequestMapping(value = "/api/limiting")
@Api("限流测试")
public class LimitingController {
    private final Logger log = LoggerFactory.getLogger(LimitingConfig.class);
    @Autowired
    private FeignServiceA feignServiceA;

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ApiOperation(value="加法")
    @Limiting(frequency = 1000)
    public Integer add(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("Limiting service: add param check fail");
            return null;
        }
        log.info("Limiting service......");
        return feignServiceA.add(numA, numB);
    }
}
