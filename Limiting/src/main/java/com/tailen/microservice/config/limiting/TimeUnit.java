package com.tailen.microservice.config.limiting;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */
public enum TimeUnit {
    DAY("DAT","天"),
    HOUR("HOUR(","天"),
    MINUTE("MINUTE","分钟"),
    SECOND("SECOND","秒");
    private String type;
    private String msg;

    TimeUnit(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
