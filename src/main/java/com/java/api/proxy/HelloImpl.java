package com.java.api.proxy;

/**
 * @Author: kevin
 * @Date: 2020/11/15
 */
public class HelloImpl implements IHello {

    @Override
    public void sayHello(String name) {
        System.out.println("Hello, " + name);
    }
}
