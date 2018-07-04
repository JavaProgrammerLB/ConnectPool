package com.yitianyigexiangfa.pool.jedis;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author Bill Lau
 * @date 2018-07-04
 */
public class JedisPoolAbstract extends Pool<Jedis> {

    public JedisPoolAbstract() {
        super();
    }

    public JedisPoolAbstract(GenericObjectPoolConfig poolConfig, PooledObjectFactory<Jedis> factory) {
        super(poolConfig, factory);
    }

    @Override
    protected void returnBrokenResource(Jedis resource) {
        super.returnBrokenResource(resource);
    }

    @Override
    protected void returnResource(Jedis resource) {
        super.returnResource(resource);
    }
}
