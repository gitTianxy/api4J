package com.java.api.proxy.cglib;

import com.java.api.proxy.Programmer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: kevin
 * @Date: 2019/4/18
 */
public class CglibDemo {

    /** 拦截器 */
    static MethodInterceptor mi = new MethodInterceptor() {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("**** actions before");
            methodProxy.invokeSuper(o, objects);
            System.out.println("**** actions after");
            return null;
        }
    };

    static <T> T generateProxy(Class<T> cls) {
        //cglib 中加强器，用来创建动态代理
        Enhancer enhancer = new Enhancer();
        // 设置被代理类
        enhancer.setSuperclass(cls);
        // 设置回调: Callback对interceptor的所有intercept()方法进行拦截
        enhancer.setCallback(mi);
        return (T) enhancer.create();
    }

    public static void main(String[] args) {
        Programmer proxy = generateProxy(Programmer.class);
        proxy.code();
    }

}
