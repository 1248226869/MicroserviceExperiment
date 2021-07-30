package com.cxytiandi.sharding.experiment.service;

import com.cxytiandi.sharding.experiment.domain.Swagger2Vo;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-24
 */

public interface SwaggerDemoService {
    Swagger2Vo getSwagger2VoInfoByName(String name);
    Swagger2Vo getSwagger2VoInfoByVersion(Integer name);
}
