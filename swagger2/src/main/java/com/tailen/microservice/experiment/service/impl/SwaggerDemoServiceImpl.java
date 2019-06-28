package com.tailen.microservice.experiment.service.impl;

import com.tailen.microservice.experiment.domain.Swagger2Vo;
import com.tailen.microservice.experiment.service.SwaggerDemoService;
import org.springframework.stereotype.Service;

/**
 * @author zhao tailen
 * @description
 * @date 2019-06-24
 */
@Service
public class SwaggerDemoServiceImpl implements SwaggerDemoService {

    @Override
    public Swagger2Vo getSwagger2VoInfoByName(String name) {
        Swagger2Vo swagger2Vo = new Swagger2Vo();
        swagger2Vo.setName(name);
        swagger2Vo.setVersion(2);
        return swagger2Vo;
    }

    @Override
    public Swagger2Vo getSwagger2VoInfoByVersion(Integer versin) {
        Swagger2Vo swagger2Vo = new Swagger2Vo();
        swagger2Vo.setName("swagger2");
        swagger2Vo.setVersion(versin);
        return swagger2Vo;
    }
}
