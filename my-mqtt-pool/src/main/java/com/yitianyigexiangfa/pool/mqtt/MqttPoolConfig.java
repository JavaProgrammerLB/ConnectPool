package com.yitianyigexiangfa.pool.mqtt;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author Bill Lau
 * @date 2018-07-05
 */
public class MqttPoolConfig extends GenericObjectPoolConfig {

    public MqttPoolConfig() {
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }
}

