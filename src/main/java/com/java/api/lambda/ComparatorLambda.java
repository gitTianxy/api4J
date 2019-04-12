package com.java.api.lambda;

import com.java.api.lambda.bean.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Description: 使用lambda表达式生成Comparator实例
 * @Author: kevin
 * @Date: 2019/1/25
 */
public class ComparatorLambda {
    public static void main(String[] args) {
        Comparator<Person> ageComp = (Person p1, Person p2) -> {
            if (p1.getAge() > p2.getAge()) {
                return 1;
            } else if (p1.getAge() == p2.getAge()) {
                return 0;
            } else {
                return -1;
            }
        };

        List<Person> pl = new ArrayList<>();
        pl.add(new Person("p1", 30));
        pl.add(new Person("p2", 20));
        Collections.sort(pl, ageComp);

        for (Person p : pl) {
            System.out.println(p);
        }
    }

}
