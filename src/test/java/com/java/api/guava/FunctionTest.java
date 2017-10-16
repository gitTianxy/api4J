package com.java.api.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.java.api.guava.funcidioms.FunctionDemo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Created by kevintian on 2017/10/16.
 */
public class FunctionTest {
    FunctionDemo demo;
    Map<String, Integer> map;

    @Before
    public void before() {
        demo = new FunctionDemo();
        map = Maps.newHashMap();
        int i=0;
        while (i++<10) {
            map.put(String.valueOf(i), i);
        }
    }

    @Test
    public void testForMap() {
        Function<String, Integer> mapFunc = demo.forMap(map);
        System.out.println(String.format("map.get(%s)=%s", 1, mapFunc.apply("1")));
        System.out.println(String.format("map.get(%s)=%s", 2, mapFunc.apply("2")));
        System.out.println(String.format("map.get(%s)=%s", 11, mapFunc.apply("11")));
    }

    @Test
    public void testCompose() {
        Function<String, Integer> f = Functions.forMap(map, null);
        Function<Integer, String> g = new Function<Integer, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Integer f) {
                if (f == null) {
                    return null;
                } else {
                    return String.valueOf(f*f);
                }
            }
        };
        Function<String, String> composeFunc = demo.compose(f, g);
        System.out.println(String.format("composeFunc.apply(%s)=%s", 1, composeFunc.apply("1")));
        System.out.println(String.format("composeFunc.apply(%s)=%s", 2, composeFunc.apply("2")));
        System.out.println(String.format("composeFunc.apply(%s)=%s", 11, composeFunc.apply("11")));
    }

    @Test
    public void testConst() {
        final Integer constVal = 5;
        Function constFunc = demo.constant(constVal);
        int i=0;
        while (i++<10) {
            Assert.assertEquals(constVal, constFunc.apply(i));
        }
    }
}
