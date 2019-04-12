package com.java.api.lambda;

import com.java.api.lambda.bean.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Description: 使用lambda表达式生成Predicate对象, 作为filter
 * @Author: kevin
 * @Date: 2019/1/25
 */
public class PredicateLambda {
    static boolean isJunior(Person pIn) {
        Predicate<Person> judger = (Person p) -> p.getAge() >= 8 && p.getAge() < 18;
        return judger.test(pIn);
    }

    public static void main(String[] args) {
        List<Person> pl = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            pl.add(new Person("p_" + i, (int) Math.round(Math.random() * 50)));
        }

        for (Person p : pl) {
            System.out.println(p + " is junior:" + isJunior(p));
        }

    }
}
