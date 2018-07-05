package com.yitianyigexiangfa.pool.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

/**
 * @author Bill Lau
 * @date 2018-07-05
 */
public class MqttPoolTest {

    @Test
    public void publishTeset(){
        String result = MqttUtil.publish("aaa", new MqttMessage(new byte[]{0x00, 0x01}));
        System.out.println("here");
        System.out.println("result is:" + result);
    }

}
