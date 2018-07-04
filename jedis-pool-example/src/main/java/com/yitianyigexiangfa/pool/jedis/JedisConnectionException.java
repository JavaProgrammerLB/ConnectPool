package com.yitianyigexiangfa.pool.jedis;

/**
 * @author Bill Lau
 * @date 2018-07-04
 */
public class JedisConnectionException extends JedisException {
    private static final long serialVersionUID = 3878126572474819403L;

    public JedisConnectionException(String message) {
        super(message);
    }

    public JedisConnectionException(Throwable cause) {
        super(cause);
    }

    public JedisConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
