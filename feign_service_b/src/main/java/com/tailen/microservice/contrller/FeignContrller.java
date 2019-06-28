package com.tailen.microservice.contrller;

import com.tailen.microservice.FeignServiceBApplication;
import com.tailen.microservice.manager.FeignServiceA;
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
 * @date 2019-06-28
 */
@RestController
@RequestMapping(value = "/api")
@Api(tags="fegin service b")
public class FeignContrller {
    private final Logger log = LoggerFactory.getLogger(FeignServiceBApplication.class);
    @Autowired
    private DemoService demoService;
    @Autowired
    private FeignServiceA feignServiceA;
    @ApiOperation(value="乘法")
    @RequestMapping(value = "/multiplication", method = RequestMethod.GET)
    public Integer multiplication(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service b : multiplication param check fail");
            return null;
        }
        log.info("fegin service b provide service:multiplication");
        return demoService.multiplication(numA, numB);
    }
    @ApiOperation(value="feign调用A服务加法")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add(@RequestParam(value = "numA") Integer numA, @RequestParam(value = "numB") Integer numB) {
        if (Objects.isNull(numA) || Objects.isNull(numB)) {
            log.warn("fegin service a : add param check fail");
            return null;
        }
        log.info("fegin service b remote use fegin service a ");
        return feignServiceA.add(numA, numB);
    }

}
