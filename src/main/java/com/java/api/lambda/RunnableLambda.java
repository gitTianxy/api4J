package com.java.api.lambda;

/**
 * @Description: 使用lambda表达式生成Runnable对象
 * @Author: kevin
 * @Date: 2019/1/25
 */
public class RunnableLambda {
    public static void main(String[] args) {
        Runnable r = () -> System.out.print("expression do in `run()`");
        r.run();
    }
}
