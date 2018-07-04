package com.yitianyigexiangfa.redis;

import redis.clients.jedis.Jedis;

/**
 * @author Bill Lau
 * @date 2018-07-04
 */
public class JedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("liu", "bei");
        String value = jedis.get("liu");
        System.out.println(value);
    }
}
