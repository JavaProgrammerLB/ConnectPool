package com.yitianyigexiangfa.pool.mqtt;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.PrintWriter;
import java.util.Deque;

/**
 * @author Bill Lau
 * @date 2018-07-05
 */
public class MqttPool<MqttClient> implements PooledObject<MqttClient> {

    protected GenericObjectPool<MqttClient> internalPool;

    public MqttPool(MqttPoolConfig config, String host, String clientId, String userName, String password) {
       this.internalPool = new GenericObjectPool<MqttClient>(new MqttFactory(host, clientId, userName, password));
       internalPool.setConfig(config);
    }

    @Override
    public MqttClient getObject() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getCreateTime() {
        return 0;
    }

    @Override
    public long getActiveTimeMillis() {
        return 0;
    }

    @Override
    public long getIdleTimeMillis() {
        return 0;
    }

    @Override
    public long getLastBorrowTime() {
        return 0;
    }

    @Override
    public long getLastReturnTime() {
        return 0;
    }

    @Override
    public long getLastUsedTime() {
        return 0;
    }

    @Override
    public int compareTo(PooledObject<MqttClient> pooledObject) {
        return 0;
    }

    @Override
    public boolean startEvictionTest() {
        return false;
    }

    @Override
    public boolean endEvictionTest(Deque<PooledObject<MqttClient>> deque) {
        return false;
    }

    @Override
    public boolean allocate() {
        return false;
    }

    @Override
    public boolean deallocate() {
        return false;
    }

    @Override
    public void invalidate() {

    }

    @Override
    public void setLogAbandoned(boolean b) {

    }

    @Override
    public void use() {

    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {

    }

    @Override
    public PooledObjectState getState() {
        return null;
    }

    @Override
    public void markAbandoned() {

    }

    @Override
    public void markReturning() {

    }
}
