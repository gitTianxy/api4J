package com.java.api.dynamicclass.javassist;

import com.java.api.dynamicclass.MyClassLoader;
import javassist.*;

import java.io.IOException;

/**
 * @Author: kevin
 * @Date: 2019/4/18
 */
public class JavassistDemo {

    static final MyClassLoader mcl = new MyClassLoader();

    public static void main(String[] args) throws Exception {
        generateClass("proxy.javassist.Programmer", "/Users/kevin/Workspace/java/api4J/result/");
    }

    /**
     * 动态创建如下类的.class文件:
     * <p>
     * package com.java.api.proxy.javassist
     * <p>
     * public class Programmer {
     * public void code(）{
     * System.out.println("Hello, ASM!");
     * }
     * }
     */
    static void generateClass(String className, String classFileFolder) throws CannotCompileException,
            NotFoundException, IOException {
        ClassPool pool = ClassPool.getDefault();
        //创建Programmer类
        CtClass cc = pool.makeClass(className);
        //定义code方法
        CtMethod method = CtNewMethod.make("public void code(){}", cc);
        //插入方法代码
        method.insertBefore("System.out.println(\"Hello, javassist!\");");
        cc.addMethod(method);
        // save .class
        cc.writeFile(classFileFolder);
    }

}
