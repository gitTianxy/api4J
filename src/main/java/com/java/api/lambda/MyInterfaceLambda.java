package com.java.api.lambda;

/**
 * @Description: 用lambda表达式生成自定义的接口, 由此抽象出lambda表达式的一般性功能定义:
 * lambda表达式可以生成任意函数式接口的的实例
 *
 * @Author: kevin
 * @Date: 2019/1/25
 */
public class MyInterfaceLambda {
    public static void main(String[] args) {
        MyInterface mi = () -> System.out.print("this is `myMethod` of `MyInterface`");
        mi.myMethod();
        mi.defaultMethod();
        System.out.println(mi.equals(1));
        System.out.println(mi.equals(mi));


    }
}
