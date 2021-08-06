package com.cxytiandi.sharding.config.limiting.domain;

/**
 * @Description
 * 令牌桶算法
 * 以r（r=时间周期/限流值）的速度向令牌桶中增加令牌，直到令牌桶满，请求到达时向令牌桶请求令牌，如获取到令牌则通过请求，否则触发限流策略
 * @Author zhao tailin
 * @Date 2021/8/6
 * @Version 1.0.0
 */
public class TokenBucketLimitingDomain {
}
