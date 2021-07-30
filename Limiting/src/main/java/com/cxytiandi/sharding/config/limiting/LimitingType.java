package com.cxytiandi.sharding.config.limiting;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */
public enum LimitingType {

    LEAKY_BUCKET("LEAKY_BUCKET",""),
    TOKEN_BUCKET("TOKEN_BUCKET","");

    private String type;
    private String msg;

    LimitingType(String type, String msg) {
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
