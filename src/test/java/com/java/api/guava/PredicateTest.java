package com.java.api.guava;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.java.api.guava.funcidioms.PredicateDemo;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kevintian on 2017/10/16.
 */
public class PredicateTest {
    PredicateDemo demo;
    List<Integer> nums;
    Map<String, Integer> numMap;

    @Before
    public void prepare() {
        demo = new PredicateDemo();
        nums = Lists.newArrayList();
        numMap = Maps.newHashMap();
        int i = 0;
        while (i++ < 20) {
            nums.add(i);
            numMap.put(String.valueOf(i), i);
        }
    }

    @Test
    public void testFilter() {
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input > 10 ? true : false;
            }
        };
        // Iterables.filter
        Iterable<Integer> iterable = demo.filter(nums, predicate);
        // Iterators.filter
        Iterator<Integer> iterator = demo.filter(nums.iterator(), predicate);
        // Collections2.filter
        Collection<Integer> collection = demo.filter(nums, predicate);
        // Maps: filterByKey
        Predicate<String> keyFilter = new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String key) {
                return Integer.valueOf(key) > 10 ? true : false;
            }
        };
        Map<String, Integer> keyMap = demo.filterByKey(numMap, keyFilter);
        // Maps: filterByValue
        Predicate<Integer> valFilter = predicate;
        Map<String, Integer> valMap = demo.filterByValue(numMap, valFilter);
        // Maps: filterByEntry
        Predicate<Map.Entry<String, Integer>> entryFilter = new Predicate<Map.Entry<String, Integer>>() {
            @Override
            public boolean apply(@Nullable Map.Entry<String, Integer> entry) {
                return (Integer.valueOf(entry.getKey()) > 10 || entry.getValue() > 10) ? true : false;
            }
        };
        Map<String, Integer> entryMap = demo.filterByEntry(numMap, entryFilter);
    }

    @Test
    public void testFind() {
        Predicate<Integer> greaterThanTen = new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer input) {
                return input > 10 ? true : false;
            }
        };
        Integer numGreaterThanTen = demo.find(nums, greaterThanTen, null);
        System.out.println(String.format("one number greater than 10 in %s: %s", nums,
                numGreaterThanTen));
        Predicate<Integer> betweenFiveAndTen = new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer input) {
                return (input > 5 && input < 10) ? true : false;
            }
        };
        Integer numBetweenFiveAndTen = demo.find(nums.iterator(), betweenFiveAndTen, null);
        System.out.println(String.format("one number between 5 and 10 in %s: %s", nums, numBetweenFiveAndTen));
    }

    @Test
    public void testCondition() {
        Predicate<Integer> greaterThanTen = new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer input) {
                return input > 10 ? true : false;
            }
        };
        // iterable: match all
        System.out.println(String.format("all items in %s are greater than 10: %s", nums, demo.matchAll(nums, greaterThanTen)));
        // iterator: match all
        System.out.println(String.format("all items in %s are greater than 10: %s", nums, demo.matchAll(nums.iterator(), greaterThanTen)));
        // iterable: match any
        System.out.println(String.format("any items in %s are greater than 10: %s", nums, demo.matchAny(nums, greaterThanTen)));
        // iterator: match any
        System.out.println(String.format("any items in %s are greater than 10: %s", nums, demo.matchAny(nums.iterator(), greaterThanTen)));
    }

    @Test
    public void testRemove() {
        Predicate<Integer> greaterThanTen = new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer input) {
                return input > 10 ? true : false;
            }
        };
        System.out.println("---original list: " + nums);
        if (demo.remove(nums, greaterThanTen)) {
            System.out.println(nums);
        }
        Predicate<Integer> lessThanFive = new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer input) {
                return input < 5 ? true : false;
            }
        };
        if (demo.remove(nums, lessThanFive)) {
            System.out.println(nums);
        }
    }
}
