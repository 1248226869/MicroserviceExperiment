package com.cxytiandi.sharding.config.jwt;

/**
 * @author zhao tailen
 * @description
 * @date 2019-05-06
 */

public class JwtException extends Exception {
    private String msg;
    public JwtException() {
        super();
    }
    public JwtException(String msg) {
        super(msg);
        this.msg=msg;
    }

}
