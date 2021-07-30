package com.cxytiandi.sharding.experiment.domain;

import java.io.Serializable;

/**
 * @author zhao tailen
 * @description swagger2的展示实体类
 * @date 2019-06-24
 */

public class Swagger2Vo implements Serializable {

    private static final long serialVersionUID = 7273151428799731259L;
    private String name;
    private Integer version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Swagger2Vo{" +
                "name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
