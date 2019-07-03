package com.tailen.microservice.controller;

import com.tailen.microservice.service.Demoservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-03
 */
@RestController
@RequestMapping(value = "/api/thread")
@Api("线程池的使用")
public class ThreadPoolAsyncControllor {
    private final Logger log = LoggerFactory.getLogger(ThreadPoolAsyncControllor.class);
    @Autowired
    private Demoservice demoservice;

    @RequestMapping(value = "/noResult", method = RequestMethod.GET)
    @ApiOperation(value = "无返回结果")
    public String noResultTask(@RequestParam(value = "name") String name) {
        log.info("无返回结果:task name is {}", name);
        try {
            demoservice.noResultTask(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "无返回结果任务执行完成";
    }

    @RequestMapping(value = "/future", method = RequestMethod.GET)
    @ApiOperation(value = "future")
    public String future(@RequestParam("tasnamekName") String name) {
        log.info("future类型:task name is {}", name);
        String result = null;
        try {
            Future<String> stringFuture = demoservice.futureTask(name);
            result = stringFuture.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("future类型执行完成");
        return result;
    }
}
