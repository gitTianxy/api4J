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
