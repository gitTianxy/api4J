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

## ObjectInputStream/ObjectOutputStream
* 序列化方法：
```java
ObjectOutputStream.writeObject(o);
```
* 反序列化方法：
```java
ObjectInputStream.readObject();
```

