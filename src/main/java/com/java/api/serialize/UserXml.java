package com.java.api.serialize;

import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * POINTS:
 * 1. password序列化前加密, 通过重写定制readObject()和writeObject()方法
 * 2. description字段为transient
 * 3. 定义compareTo方法用于比较原对象和反序列化生成的对象
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "User")
@XmlType(propOrder = {
        "age",
        "sex",
        "address",
        "phone",
        "password",
        "elements",
})
@Data
public class UserXml {
    @XmlAttribute(required = true)
    private String name;
    private int age;
    private String sex;
    private String address;
    private String phone;
    @XmlElement(name = "passwd")
    @XmlJavaTypeAdapter(PWDAdapter.class)
    private String password;
    private Elements elements;
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
                ", elements=" + elements +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean compareTo(UserXml u) {
        return (name == u.getName()) && (phone == u.getPhone());
    }

    static class PWDAdapter extends XmlAdapter<String, String> {

        @Override
        public String unmarshal(String v) throws Exception {
            return Integer.valueOf(Integer.valueOf(v).intValue() >> 2).toString();
        }

        @Override
        public String marshal(String v) throws Exception {
            return Integer.valueOf(Integer.valueOf(v).intValue() << 2).toString();
        }
    }

    @XmlRootElement(name = "elements")
    @XmlAccessorType(XmlAccessType.FIELD)
    static class Elements {
        @XmlElement(name = "element")
        List<Element> elements;

        public Elements() {}

        public Elements(List<Element> elements) {
            this.elements = elements;
        }

        @Override
        public String toString() {
            return elements.toString();
        }
    }

    @XmlRootElement(name = "element")
    @XmlAccessorType(XmlAccessType.FIELD)
    @Data
    static class Element {
        int id;
        String title;

        @Override
        public String toString() {
            return "Element{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
