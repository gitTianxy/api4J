package com.java.api.guava.reflection;

import lombok.Data;

/**
 * Created by kevintian on 2017/10/17.
 */
@Data
public class Student implements People {
    String name;
    int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void print() {
        System.out.println("name: " + name + ", age: " + age);
    }
}
