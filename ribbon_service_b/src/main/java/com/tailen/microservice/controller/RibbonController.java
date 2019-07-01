package com.tailen.microservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-01
 */
@RestController
@RequestMapping(value = "/api")
@Api(tags="ribbon service b")
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/b/set")
    @ApiOperation(value="b调a")
    public String set() {
        return restTemplate.getForObject("http://eureka-client/api/a/set", String.class);
    }

}
