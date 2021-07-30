package com.cxytiandi.sharding.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-03
 */

public interface Demoservice {
    void noResultTask(String taskName) throws Exception;

    Future<String> futureTask(String taskName) throws Exception;
}
