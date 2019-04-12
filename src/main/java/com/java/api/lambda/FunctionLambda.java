package com.java.api.lambda;

import com.java.api.lambda.bean.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Description: 使用lambda表达式生成Function对象, 用作transformer
 * @Author: kevin
 * @Date: 2019/1/25
 */
public class FunctionLambda {
    static String getDescription(Person pIn) {
        Function<Person, String> trans = (Person p) -> "[Description] Name:" + p.getName() + ", Age:" + p.getAge();
        return trans.apply(pIn);
    }

    static int getAgeSum(List<Person> plIn) {
        Function<List<Person>, Integer> trans = (List<Person> pl) -> {
            int sum = 0;
            for (Person p : pl) {
                sum += p.getAge();
            }
            return sum;
        };

        return trans.apply(plIn);
    }

    public static void main(String[] args) {
        List<Person> pl = new ArrayList<>();
        pl.add(new Person("p1", 30));
        pl.add(new Person("p2", 20));

        for (Person p: pl) {
            System.out.println(getDescription(p));
        }

        System.out.println("[Sum]" + getAgeSum(pl));
    }

}
