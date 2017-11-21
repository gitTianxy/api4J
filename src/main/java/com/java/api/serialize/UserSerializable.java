package com.java.api.serialize;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Data
public class UserSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private String sex;
    private String address;
    private String phone;
    private String password;
    private transient String discription;

    @Override
    public String toString() {
        return name + "{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", discription='" + discription + '\'' +
                '}';
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
