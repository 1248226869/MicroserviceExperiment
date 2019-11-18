package com.tailen.microservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author zhao tailen
 * @description
 * @date 2019-11-01
 */
@ConfigurationProperties(prefix = "xx")
@Configuration
public class TXMachinesProperties {

    private List<Map<String, String>> machine1s;

    public List<Map<String, String>> getMachine1s() {
        return machine1s;
    }

    public void setMachine1s(List<Map<String, String>> machine1s) {
        this.machine1s = machine1s;
    }

}
