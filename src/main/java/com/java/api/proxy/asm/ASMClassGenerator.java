package com.java.api.proxy.asm;


import com.java.api.proxy.MyClassLoader;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * ASM 是一个 Java 字节码操控框架。它能够以二进制形式修改已有类或者动态生成类。
 * ASM 可以直接产生二进制 class 文件，也可以在类被加载入 Java 虚拟机之前动态改变类行为。
 * ASM 从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生成新类。
 *
 * 不过ASM在创建class字节码的过程中，操纵的级别是底层JVM的汇编指令级别，
 * 这要求ASM使用者要对class组织结构和JVM汇编指令有一定的了解。
 *
 * @Author: kevin
 * @Date: 2019/4/18
 */
public class ASMClassGenerator {
    static final ClassWriter cw = new ClassWriter(0);

    static final MyClassLoader mcl = new MyClassLoader();

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String classFilePath = "/Users/kevin/Workspace/java/api4J/result/proxy/asm/Programmer.class";
        genereteCls(classFilePath);
        Class aClass = mcl.findClass(classFilePath);
        Object obj = aClass.newInstance();
        aClass.getMethod("code").invoke(obj);
    }

    /**
     * 使用ASM框架提供了`ClassWriter` 接口，通过访问者模式进行动态创建如下类的.class文件:
     *
     *  public class Programmer {
     *      public void code(）{
     *          System.out.println("Hello, ASM!");
     *      }
     *  }
     */
    static void genereteCls(String classFilePath) throws IOException {
        // visit: 定义类的头部信息
        cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC,
                "Programmer",
                null, "java/lang/Object", null);
        // visitMethod: 创建构造函数
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>","()V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        // visitMethod: 创建方法
        mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "code", "()V",
                null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello, ASM!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V");
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(2, 2);
        mv.visitEnd();

        // write class
        try (FileOutputStream fos = new FileOutputStream(classFilePath)) {
            fos.write(cw.toByteArray());
            fos.flush();
        }
    }
}
