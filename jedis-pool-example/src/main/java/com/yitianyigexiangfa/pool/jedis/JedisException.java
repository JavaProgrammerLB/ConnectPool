package com.yitianyigexiangfa.pool.jedis;

/**
 * @author Bill Lau
 * @date 2018-07-04
 */
public class JedisException extends RuntimeException {
    private static final long serialVersionUID = -2946266495682282677L;

    public JedisException(String message) {
        super(message);
    }

    public JedisException(Throwable e) {
        super(e);
    }

    public JedisException(String message, Throwable cause) {
        super(message, cause);
    }
}

