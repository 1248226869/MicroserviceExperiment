package com.tailen.microservice.experiment.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-24
 */
@RestController
@RequestMapping(value="/api")
@Api(value="用户操作接口",tags={"用户操作接口"})
public class SwaggerDemoController {
}
