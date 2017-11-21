# Java Serialization
* commons
* ObjectInputStream/ObjectOutputStream

---
## commons
* java提供的序列化机制，可以将一个java对象转换成对应的字节码序列，也可以基于对象的字节码序列重构出原对象；
前者称为"序列化"，后者称为"反序列化"。
* 对象的字节码序列包括：对象所属的类信息，对象属性的类信息，对象包含的数据。
* java序列化是JVM独立的——同样的序列化结果可以在不同的平台上解析运行出相同的结果。
* java对象可以序列化的前提条件是：对象所属的类以及类中所有属性都实现了`Serializable`接口
* 常用的序列化/反序列化api有：
    - ObjectInputStream/ObjectOutputStream
    - jackson
    - gson
* 序列化ID
    - 虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致（就是 private static final long serialVersionUID = 1L）
    - 序列化 ID 在 Eclipse 下提供了两种生成策略，一个是固定的 1L，一个是随机生成一个不重复的 long 类型数据（实际上是使用 JDK 工具生成），在这里有一个建议，如果没有特殊需求，就是用默认的 1L 就可以，这样可以确保代码一致时反序列化成功。那么随机生成的序列化 ID 有什么作用呢，有些时候，通过改变序列化 ID 可以用来限制某些用户的使用。

## 通用方法``
* 序列化：
```java
ObjectOutputStream.writeObject(o);
```
* 反序列化：
```java
ObjectInputStream.readObject();
```

## 自定义方法
* transient: 定义不序列化的字段

* writeObject(ObjectOutputStream oos): 重写对象的序列化方法

* readObject(ObjectInputStream ois): 重写对象的反序列化方法

* writeReplace(): 在序列化的时候改变目标的类型(obj_origin-->string 变成 obj_repl-->string)

* readReplace(): 反序列化的时候对目标的类型进行更改(string-->obj_origin 变成 string-->obj_origin-->obj_repl)
