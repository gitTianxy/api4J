package com.java.api.dynamicclass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: kevin
 * @Date: 2019/4/18
 */
public class MyClassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String classFilePath) throws ClassNotFoundException {
        byte[] cLassBytes = null;
        Path path = Paths.get(classFilePath);
        try {
            cLassBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(cLassBytes, 0, cLassBytes.length);
    }
}
