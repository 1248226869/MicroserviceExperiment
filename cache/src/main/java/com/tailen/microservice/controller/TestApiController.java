package com.tailen.microservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhao tailen
 * @description
 * @date 2019-11-13
 */
@RestController
@RequestMapping(value = "/api")
@Api(tags="缓存测试")
public class TestApiController {
    private final Logger log = LoggerFactory.getLogger(TestApiController.class);


    //    static {
//        log.info("set cache init start...........");
//        CacheSpaceContainer cacheSpaceContainer=CacheSpaceContainer.getCacheSpaceContainer();
//        CacheSpace cacheSpace1=new CacheSpace();
//        cacheSpace1.setName("static");
//        cacheSpace1.setCachePriority(CachePriority.ONLY_LOCAL);
//        cacheSpaceContainer.add(cacheSpace1);
//        log.info("set cache init end...........");
//    }

    @ApiOperation(value="getLocalName")
    @Cacheable( value = "local",  //使用{name}缓存
            key = "#name + '/name/local/get'", //缓存的key为动态的id+静态的''/live/test'
            condition = "#id != '' ", //过滤掉参数id为空的情况
            unless = "#result == null" //过滤掉执行结果为空的情况
    )
    @RequestMapping(value="/name/local/get")
    @ResponseBody
    public PersionDTO getLocalName(@RequestParam(required=false) String name) {
        log.info("getLocalName: no cache. param is {}",name);
        return new PersionDTO(name,18);
    }

    @ApiOperation(value="getRemoteName")
    @Cacheable( value = "remote",  //使用{name}缓存
            key = "#name + '/name/remote/get'", //缓存的key为动态的id+静态的''/live/test'
            condition = "#id != '' ", //过滤掉参数id为空的情况
            unless = "#result == null" //过滤掉执行结果为空的情况
    )
    @RequestMapping(value="/name/remote/get")
    @ResponseBody
    public PersionDTO getRemoteName(@RequestParam(required=false) String name) {

        log.info("getRemoteName:no cache. param is {}",name);
        return new PersionDTO(name,21);
    }

    @ApiOperation(value="getName")
    @Cacheable( value = "firstRemote",  //使用{name}缓存
            key = "#name + 'name/get'", //缓存的key为动态的id+静态的''/live/test'
            condition = "#id != '' ", //过滤掉参数id为空的情况
            unless = "#result == null" //过滤掉执行结果为空的情况
    )
    @RequestMapping(value="/name/get")
    @ResponseBody
    public PersionDTO getName(@RequestParam(required=false) String name) {

        log.info("no cache. param is {}",name);
        return new PersionDTO(name,25);
    }
}
