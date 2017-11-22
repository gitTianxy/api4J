package com.java.api.serialize;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * POINTS:
 * 1. password序列化前加密, 通过重写定制readObject()和writeObject()方法
 * 2. description字段为transient
 * 3. 定义compareTo方法用于比较原对象和反序列化生成的对象
 */
@Data
public class UserSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String sex;
    private String address;
    private String phone;
    private String password;
    private transient String description;

    @Override
    public String toString() {
        return name + "{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean compareTo(UserSerializable u) {
        return (name == u.getName()) && (phone == u.getPhone());
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        String passwd = this.password;
        // 加密
        this.password = Integer.valueOf(Integer.valueOf(password).intValue() << 2).toString();
        oos.defaultWriteObject();
        this.password = passwd;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // 解密
        if (this.password != null) {
            this.password = Integer.valueOf(Integer.valueOf(password).intValue() >> 2).toString();
        }
    }

    private Object writeReplace() {
        return this;
    }

    private Object readResolve() {
        return this;
    }
}
