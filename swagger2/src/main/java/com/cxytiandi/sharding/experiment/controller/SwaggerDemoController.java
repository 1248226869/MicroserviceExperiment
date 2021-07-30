package com.cxytiandi.sharding.experiment.controller;

import com.cxytiandi.sharding.experiment.domain.Swagger2Vo;
import com.cxytiandi.sharding.experiment.service.SwaggerDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-24
 */
@RestController
@RequestMapping(value="/api/swagger")
@Api(tags="swagger2 demo")
public class SwaggerDemoController {

    @Autowired
    private SwaggerDemoService swaggerDemoService;

    @ApiOperation(value="获取swagger信息", notes="根据name获取swagger信息")
    @ApiImplicitParam(name = "name", value = "swagger名称", required = true,paramType = "query", dataType = "String")
    @GetMapping(value="/get/name")
    public Swagger2Vo getSwagger2Vo(@RequestParam(value = "name")String  name){
        Swagger2Vo swagger2VoInfoByName = swaggerDemoService.getSwagger2VoInfoByName(name);
        return swagger2VoInfoByName;
    }
}
