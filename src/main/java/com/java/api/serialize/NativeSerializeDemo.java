package com.java.api.serialize;

import java.io.*;

/**
 * native method:
 * 1.
 * 2.
 * <p>
 * self-defined method:
 * 1. transient
 * 2.
 */
public class NativeSerializeDemo<T> {
    public static void main(String[] args) throws Exception {
        UserSerializable u = new UserSerializable();
        u.setName("ZhangSan");
        u.setAge(18);
        u.setPhone("136");
        u.setPassword("1234");
        u.setSex("m");
        u.setAddress("ZhangSan road");
        u.setDiscription(u.toString());

        String outputPath = "result/user_serialize.txt";

        NativeSerializeDemo<UserSerializable> demo = new NativeSerializeDemo<>();
        for(int i = 1; i<=100; i++) {
            demo.serialize(u, outputPath);
            UserSerializable out = demo.deserialize(outputPath);
            System.out.println("round" + i + ":" + out);
        }
    }

    public void serialize(T obj, String outputPath) throws Exception {
        try (FileOutputStream fileOs = new FileOutputStream(new File(outputPath));
             ObjectOutputStream objOs = new ObjectOutputStream(fileOs)) {
            objOs.writeObject(obj);
            objOs.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    public T deserialize(String inputPath) throws Exception {
        try (FileInputStream fin = new FileInputStream(new File(inputPath));
             ObjectInputStream oin = new ObjectInputStream(fin)) {
            return (T) oin.readObject();
        } catch (Exception e) {
            throw e;
        }
    }
}
