package com.tailen.microservice.service.impl;

import com.tailen.microservice.service.Demoservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-03
 */
@Service
public class DemoserviceImpl implements Demoservice {
    private final Logger log = LoggerFactory.getLogger(DemoserviceImpl.class);
    public static Random random = new Random();


    @Async("TaskExecutor")
    @Override
    public void noResultTask(String taskName) throws Exception {
        long sleep = random.nextInt(10000);
        log.info("开始无返回结果的任务: {},可能耗时：{} 毫秒",taskName,sleep);
        Thread.sleep(sleep);
        log.info("完成无返回结果的任务: {}",taskName);
    }

    @Async("TaskExecutor")
    @Override
    public Future<String> futureTask(String taskName) throws Exception {
        long sleep = random.nextInt(10000);
        log.info("开始Future类型的任务: {},可能耗时：{} 毫秒",taskName,sleep);
        Thread.sleep(sleep);
        String result = " 任务完成，结果返回";
        log.info("完成Future类型的任务: {}",taskName);
        return new AsyncResult<>(result);
    }
}
