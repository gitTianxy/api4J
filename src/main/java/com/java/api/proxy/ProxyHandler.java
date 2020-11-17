package com.java.api.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: kevin
 * @Date: 2020/11/15
 */
public class ProxyHandler implements InvocationHandler {
    private Object target;


    public ProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("BEFORE: " + method.getName());
        Object rs = method.invoke(target, args);
        System.out.println("AFTER: "+ method.getName());
        return rs;
    }
}
