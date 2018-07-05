package com.yitianyigexiangfa.pool.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bill Lau
 * @date 2018-07-05
 */
public class MqttUtil {

    private static ReentrantLock lock = new ReentrantLock();

    private static MqttPool<MqttClient> mqttPool;

    private static String HOST;
    private static String CLIENT_ID;
    private static String USER_NAME;
    private static String USER_PASSWORD;

    static {
        Properties prop = new Properties();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("config.properties").getInputStream()));
            prop.load(bufferedReader);
            HOST = prop.getProperty("mqtt.host");
            CLIENT_ID = prop.getProperty("mqtt.client.id");
            USER_NAME = prop.getProperty("mqtt.user");
            USER_PASSWORD = prop.getProperty("mqtt.password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized static void initMqttPool() {
        // ============= create a Pool config Object =========
        MqttPoolConfig config = new MqttPoolConfig();
        config.setMaxIdle(100);
        config.setMinIdle(50);
        config.setMaxTotal(500);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        mqttPool = new MqttPool<MqttClient>(config, HOST, CLIENT_ID, USER_NAME, USER_PASSWORD);
    }

    private static MqttClient getMqttClient() {
        if (mqttPool == null) {
            lock.lock();
            if (mqttPool == null) {
                initMqttPool();
            }
            lock.unlock();
        }

        // =========== use Pool getResource method =========
        return mqttPool.getObject();
    }

    public static String publish(String topic, MqttMessage mqttMessage){
        String result = null;
        MqttClient mqttClient = MqttUtil.getMqttClient();
        if(mqttClient == null){
            System.out.println("mqttClient is null");
            return result;
        }

        try {
            mqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        result = "success";
        return result;
    }

}
