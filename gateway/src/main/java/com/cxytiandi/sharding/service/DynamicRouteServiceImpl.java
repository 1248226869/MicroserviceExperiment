package com.cxytiandi.sharding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/27
 * @Version 1.0.0
 */
@Component
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    /**
     * 增加路由
     * @param definition 路由模型
     * @return 增加结果
     */
    public void add(RouteDefinition definition){
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 修改路由
     * @param definition 路由模型
     * @return 修改结果
     */
    public void update(RouteDefinition definition){
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            throw new RouteException("update fail, not find route routeId："+definition.getId());
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            throw new RouteException("update route fail");
        }
    }

    /**
     * 删除路由
     * @param id 路由id
     * @return 删除结果
     */
    public void delete(String id){
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            throw new RouteException("delete fail");
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
