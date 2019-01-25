package com.java.api.lambda;

/**
 * 函数式接口规约:
 * 1. 只能声明一个自定义抽象方法
 *
 * 2. 可以声明和Object类签名一致的抽象方法: 因为Object是所有类的父类, 所有实例天然继承了Object的方法,
 * 当然也包括函数接口的生成的实例; 所以定义这样的抽象方法并不违反函数式接口的第一条规约!
 * 但是在函数是接口中这样的声明没任何意义: 因为无法在lambda表达式中重写这些方法--lambda表达式只能实现`1`中的自定义抽象方法
 *
 * 3. java 8开始可以声明静态方法. 注意它属于类!
 *
 * 4. java 8开始可以通过default关键字给接口声明`具体方法`. 它属于接口实例!
 */
@FunctionalInterface
public interface MyInterface {
    // 自定义抽象方法
    void myMethod();

    // 和Object类签名一致的抽象方法: 在函数式接口中不提倡使用!
    boolean equals(Object o);

    // 静态方法
    static void sMethod() {
        System.out.println("static method in `interface`");
    }

    // 具体方法
    default void defaultMethod() {
        System.out.println("default method in `interface`");
    }
}
