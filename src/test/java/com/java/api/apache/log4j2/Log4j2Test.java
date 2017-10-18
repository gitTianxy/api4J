package com.java.api.apache.log4j2;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by kevintian on 2017/10/18.
 */
public class Log4j2Test {
    Log4j2Demo demo;

    @Before
    public void prepare() {
        demo = new Log4j2Demo();
    }

    @Test
    public void testRootLogging() {
        demo.rootLogging();
    }

    @Test
    public void testMyLogging() throws InterruptedException {
        demo.myLogging();
    }

    @Test
    public void testAsyncLogging() {
        demo.asyncLogging();
    }

    @Test
    public void testMongoLogging() {
        demo.mongoLogging();
    }
}
