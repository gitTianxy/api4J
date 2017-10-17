package com.java.api.guava.reflection;

import com.google.common.reflect.Reflection;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * CONTENT:
 * 1. dynamic proxy
 *
 * Created by kevintian on 2017/10/17.
 */
public class ReflectionDemo {

    /**
     * 比较guava和jdk原生的动态代理: guava的动态代理生成代码较jdk的更优雅
     */
    public void dynamicProxy() {
        People student = new Student("Qin Jiangbo", 23);
        // jdk dynamic proxy
        People jdkProxy = (People) Proxy.newProxyInstance(
                People.class.getClassLoader(),
                new Class[]{People.class},
                getHandler(student));
        jdkProxy.print();
        // guava dynamic proxy
        System.out.println(StringUtils.repeat("-", 50));
        People guavaProxy = Reflection.newProxy(People.class, getHandler(student));
        guavaProxy.print();
    }

    public InvocationHandler getHandler(Object proxiedObject) {
        return (proxy, method, args) -> {
            System.out.println("method name: " + method.getName());
            System.out.println("args: " + (args == null ? "null" : args));
            return method.invoke(proxiedObject, args);
        };
    }
}
