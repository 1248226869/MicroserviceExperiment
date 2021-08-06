package com.cxytiandi.sharding.config.cache.local;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
public class NotMajorKeyException extends RuntimeException {
    private static final long serialVersionUID = 5162710183389028792L;

    /**
     * Constructs a {@code NotMajorKeyException} with no detail message.
     */
    public NotMajorKeyException() {
        super();
    }

    /**
     * Constructs a {@code NotMajorKeyException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public NotMajorKeyException(String s) {
        super(s);
    }
}
