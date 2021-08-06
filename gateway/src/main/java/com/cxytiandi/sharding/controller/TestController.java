package com.cxytiandi.sharding.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/20
 * @Version 1.0.0
 */
@RestController
public class TestController {
    private final Logger log=LoggerFactory.getLogger(TestController.class);
    @RequestMapping(value="/multiplication", method=RequestMethod.GET)
    public Integer multiplication(@RequestParam(value="numA") Integer numA, @RequestParam(value="numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service b : multiplication param check fail");
            return null;
        }
        log.info("fegin service b provide service:multiplication");
        return numA*numB;
    }
}
