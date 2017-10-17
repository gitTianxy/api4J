package com.java.api.guava.reflection;

import com.google.common.reflect.Invokable;

/**
 * Guava的Invokable是对java.lang.reflect.Method和java.lang.reflect.Constructor的流式包装。
 * 它简化了常见的反射代码的使用。
 * <p>
 * Created by kevintian on 2017/10/17.
 */
public class InvokableDemo {
    public static void main(String[] args) throws NoSuchMethodException {
        // method handler
        Invokable methodHander = Invokable.from(Student.class.getMethod("print"));
        System.out.println(String.format("is public: %s, parameters: %s, name: %s, return type: %s, is static: %s",
                methodHander.isPublic(), methodHander.getParameters(), methodHander.getName(), methodHander
                        .getReturnType(), methodHander.isStatic()));
        // constructor handler
        Invokable constructHandler = Invokable.from(Student.class.getConstructor());
        System.out.println(String.format("class name: %s", constructHandler.getName()));
    }
}
