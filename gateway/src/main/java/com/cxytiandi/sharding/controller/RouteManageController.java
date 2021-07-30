package com.cxytiandi.sharding.controller;


import com.cxytiandi.sharding.domain.common.JsonReturn;
import com.cxytiandi.sharding.domain.route.GatewayFilterDefinition;
import com.cxytiandi.sharding.domain.route.GatewayPredicateDefinition;
import com.cxytiandi.sharding.domain.route.GatewayRouteDefinition;
import com.cxytiandi.sharding.service.DynamicRouteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态路由配置管理
 *
 * @author wangfengwf@chaoxing.cm
 * @since 2019-07-29 14:27
 */
@RestController
@RequestMapping("/route")
public class RouteManageController {
    private final Logger log=LoggerFactory.getLogger(RouteManageController.class);

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    /**
     * 增加路由
     *
     * @return
     */
    @PostMapping("/add")
    public @ResponseBody
    JsonReturn add(@RequestBody GatewayRouteDefinition gwdefinition) {
        try {
            RouteDefinition definition = assembleRouteDefinition(gwdefinition);
            this.dynamicRouteService.add(definition);
            return JsonReturn.success("添加成功", definition.getId());
        } catch (Exception e) {
            log.error(gwdefinition.getId() + "add route error : " + e.getMessage());
            return JsonReturn.error("添加失败：" + e.getMessage());
        }
    }

    @PostMapping("/update")
    public @ResponseBody
    JsonReturn update(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = assembleRouteDefinition(gwdefinition);
        try {
//            this.dynamicRouteService.update(definition);
            return JsonReturn.success("修改成功", gwdefinition.getId());
        } catch (Exception e) {
            log.error(gwdefinition.getId()+" update error : "+e.getMessage());
            return JsonReturn.error("修改失败："+e.getMessage());
        }
    }

    @GetMapping("/delete/{id}")
    public @ResponseBody
    JsonReturn delete(@PathVariable String id) {
        try {
//            this.dynamicRouteService.delete(id);
            return JsonReturn.success("删除成功", id);
        } catch (Exception e) {
            log.error(id + " delete error : " + e.getMessage());
            return JsonReturn.error("删除失败: " + e.getMessage());
        }
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {

        RouteDefinition definition = new RouteDefinition();

        // ID
        definition.setId(gwdefinition.getId());

        // Predicates
        List<PredicateDefinition> pdList = new ArrayList<>();
        if (gwdefinition.getPredicates() != null) {
            for (GatewayPredicateDefinition gpDefinition : gwdefinition.getPredicates()) {
                PredicateDefinition predicate = new PredicateDefinition();
                predicate.setArgs(gpDefinition.getArgs());
                predicate.setName(gpDefinition.getName());
                pdList.add(predicate);
            }
        }
        definition.setPredicates(pdList);

        // Filters
        List<FilterDefinition> fdList = new ArrayList<>();
        if (gwdefinition.getFilters() != null) {
            for (GatewayFilterDefinition gfDefinition : gwdefinition.getFilters()) {
                FilterDefinition filter = new FilterDefinition();
                filter.setArgs(gfDefinition.getArgs());
                filter.setName(gfDefinition.getName());
                fdList.add(filter);
            }
        }
        definition.setFilters(fdList);

        // URI
        URI uri = UriComponentsBuilder.fromUriString(gwdefinition.getUri()).build().toUri();
        definition.setUri(uri);

        return definition;
    }

}
