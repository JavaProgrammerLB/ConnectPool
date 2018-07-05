package com.yitianyigexiangfa.pool.mqtt;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Bill Lau
 * @date 2018-07-05
 */
public class MqttFactory<T> implements PooledObjectFactory<MqttClient> {

    private final String host;

    private final String clientId;

    private final String username;

    private final String password;

    public MqttFactory(String host, String clientId, String username, String password) {
        this.host = host;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
    }

    @Override
    public PooledObject<MqttClient> makeObject() throws Exception {

        final MqttClient client = new MqttClient(host, clientId, new MemoryPersistence());
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setCleanSession(true);
        client.connect(options);
        return new DefaultPooledObject<MqttClient>(client);
    }

    @Override
    public void destroyObject(PooledObject<MqttClient> pooledObject) throws Exception {
        final MqttClient mqttClient = pooledObject.getObject();
        if (mqttClient.isConnected()){
            mqttClient.disconnect();
        }
    }

    @Override
    public boolean validateObject(PooledObject<MqttClient> pooledObject) {
        final MqttClient mqttClient = pooledObject.getObject();
        return mqttClient.getClientId().equals(clientId) && mqttClient.getServerURI().equals(host);
    }

    @Override
    public void activateObject(PooledObject<MqttClient> pooledObject) throws Exception {
    }

    @Override
    public void passivateObject(PooledObject<MqttClient> pooledObject) throws Exception {
    }
}
