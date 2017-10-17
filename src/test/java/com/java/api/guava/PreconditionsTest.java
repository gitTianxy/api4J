package com.java.api.guava;

import com.java.api.guava.base.PreconditionDemo;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by kevintian on 2017/10/17.
 */
public class PreconditionsTest {
    PreconditionDemo demo;
    Integer[] nums;

    @Before
    public void prepare() {
        demo = new PreconditionDemo();
        nums = new Integer[]{1, 2, null, 4, null};
    }

    @Test
    public void testCheckTrue() {
        boolean boolExpr = 1 > 2;
        try {
            demo.checkTrue(boolExpr);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            demo.checkTrue(boolExpr, "'a>b' not true");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            demo.checkTrue(boolExpr, "'a>b' not true. a=%s, b=%s", 1, 2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCheckNotNull() {
        for (Integer num : nums) {
            try {
                Integer ret = demo.checkNotNull(num, "obj is null. obj=%s", num);
                System.out.println(ret);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void testCheckElementIdx() {
        int idx = 0;
        while (idx < 10) {
            try {
                int ret = demo.checkElementIdx(idx, nums.length, String.format("index=%s", idx));
                System.out.println(String.format("legal index. index=%s, size=%s", ret, nums.length));
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
            idx++;
        }
    }
}
