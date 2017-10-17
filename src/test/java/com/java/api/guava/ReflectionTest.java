package com.java.api.guava;

import com.java.api.guava.reflection.ReflectionDemo;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by kevintian on 2017/10/17.
 */
public class ReflectionTest {
    ReflectionDemo demo;

    @Before
    public void prepare() {
        demo = new ReflectionDemo();
    }

    @Test
    public void testDynamicProxy() {
        demo.dynamicProxy();
    }
}
