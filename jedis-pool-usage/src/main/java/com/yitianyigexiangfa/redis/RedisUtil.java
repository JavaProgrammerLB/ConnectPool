package com.yitianyigexiangfa.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Bill Lau
 * @date 2018-04-25
 */
public class RedisUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    private static ReentrantLock lock = new ReentrantLock();

    private static JedisPool jedisPool;

    private static String HOST;
    private static int PORT;
    private static int MIN_IDLE;
    private static int MAX_IDLE;
    private static int MAX_TOTAL;
    private static int MAX_WAIT_MILLIS;
    private static boolean TEST_ON_BORROW;
    private static int SOCKET_TIMEOUT;

    /**
     * use static code block to load properties files
     */
    static {
        Properties prop = new Properties();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("redis-host.properties").getInputStream()));
            prop.load(bufferedReader);
            HOST = prop.getProperty("redis.host");
            PORT = Integer.parseInt(prop.getProperty("redis.port"));
            MIN_IDLE = Integer.parseInt(prop.getProperty("redis.minIdle"));
            MAX_IDLE = Integer.parseInt(prop.getProperty("redis.maxIdle"));
            MAX_TOTAL = Integer.parseInt(prop.getProperty("redis.maxTotal"));
            MAX_WAIT_MILLIS = Integer.parseInt(prop.getProperty("redis.maxWaitMillis"));
            TEST_ON_BORROW = Boolean.getBoolean(prop.getProperty("redis.testOnBorrow"));
            SOCKET_TIMEOUT = Integer.parseInt(prop.getProperty("redis.socket.timeout"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * make constructor private
     */
    private RedisUtil() {
    }

    private synchronized static void initJedisPool() {
        // ============= create a Pool config Object =========
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setMaxWaitMillis(MAX_WAIT_MILLIS);
        config.setTestOnBorrow(TEST_ON_BORROW);

        URI redisUri = URI.create("redis://" + HOST + ":" + PORT);
        jedisPool = new JedisPool(config, redisUri, SOCKET_TIMEOUT);
    }

    private static Jedis getJedis() {
        if (jedisPool == null) {
            lock.lock();
            if (jedisPool == null) {
                initJedisPool();
            }
            lock.unlock();
        }

        // =========== use Pool getResource method =========
        return jedisPool.getResource();
    }

    public static String set(String key, String value) {
        return set(key, value, null);
    }

    public static String set(String key, String value, Integer timeout) {
        String result = null;

        Jedis jedis = RedisUtil.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.set(key, value);
            if (null != timeout) {
                jedis.expire(key, timeout);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    public static String get(String key) {
        String result = null;
        Jedis jedis = RedisUtil.getJedis();
        if (jedis == null) {
            return result;
        }
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    public static boolean del(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = RedisUtil.getJedis();
        if (null == jedis) {
            return Boolean.FALSE;
        }
        try {
            jedis.del(key);
        } catch (Exception e) {
            LOGGER.error("fault" + e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    public static Long append(String key, String value) {
        Long result = Long.valueOf(0);
        Jedis jedis = RedisUtil.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.append(key, value);
        } catch (Exception e) {
            LOGGER.error("fault:" + e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }


    public static Boolean exists(String key) {
        Boolean result = Boolean.FALSE;
        Jedis jedis = RedisUtil.getJedis();
        if (null == jedis) {
            return result;
        }
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            LOGGER.error("fault：，" + e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

}

