package com.java.api.serialize;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * POINTS:
 * 1. password序列化前加密, 通过重写定制readObject()和writeObject()方法
 * 2. description字段为transient
 * 3. 定义compareTo方法用于比较原对象和反序列化生成的对象
 */
@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "User")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
        "name",
        "age",
        "sex",
        "address",
        "phone",
        "password",
})
@Data
public class UserXml {
    private String name;
    private int age;
    private String sex;
    private String address;
    private String phone;
    @XmlElement(name = "passwd")
    private String password;
    @XmlTransient
    private String description;

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

    public boolean compareTo(UserXml u) {
        return (name == u.getName()) && (phone == u.getPhone());
    }

}
