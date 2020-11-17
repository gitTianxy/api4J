package com.java.api.proxy;

import java.lang.reflect.Proxy;

/**
 * @Author: kevin
 * @Date: 2020/11/15
 */
public class HelloProxyTest {
    public static void main(String[] args) {
        HelloImpl target = new HelloImpl();
        IHello proxy = (IHello) Proxy.newProxyInstance(IHello.class.getClassLoader(), new Class[]{IHello.class}, new
                ProxyHandler(target));
        proxy.sayHello("bubu~");
    }
}
