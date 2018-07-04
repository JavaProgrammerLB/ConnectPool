package com.yitianyigexiangfa.pool.jedis;

/**
 * @author Bill Lau
 * @date 2018-07-04
 */
public class JedisExhaustedPoolException extends JedisException {

    public JedisExhaustedPoolException(String message) {
        super(message);
    }

    public JedisExhaustedPoolException(Throwable e) {
        super(e);
    }

    public JedisExhaustedPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
