package com.cxytiandi.sharding.po;

import com.cxytiandi.sharding.config.cache.ObjField;

import java.io.Serializable;

/**
 * 分表
 *
 * @author yinjihuan
 */
public class User implements Serializable, ObjField {

    private static final long serialVersionUID=-1205226416664488559L;

    private Long id;

    private String city="";

    private String name="";

    private int flag=0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city=city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag=flag;
    }
}
