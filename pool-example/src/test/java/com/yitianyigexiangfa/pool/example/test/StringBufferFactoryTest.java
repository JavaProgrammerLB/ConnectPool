package com.yitianyigexiangfa.pool.example.test;

import com.yitianyigexiangfa.pool.example.ReadUtilWithPool;
import com.yitianyigexiangfa.pool.example.StringBufferFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Bill Lau
 * @date 2018-07-04
 */
public class StringBufferFactoryTest {

    @Test
    public void testPoolFactory(){
        ReadUtilWithPool readUtilWithPool = new ReadUtilWithPool(new GenericObjectPool<StringBuffer>(new StringBufferFactory()));
        try {
            String result = readUtilWithPool.readToString(new FileReader("/Users/liubei/Program/gitworkspace/JavaWork/ConnectPool/pool-example/src/main/resources/a.txt"));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
