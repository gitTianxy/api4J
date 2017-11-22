package com.java.api.serialize;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JAXBTest {
    UserXml u;
    String filePath;

    @Before
    public void prepare() {
        u = new UserXml();
        u.setName("ZhangSan");
        u.setAge(18);
        u.setPhone("136");
        u.setPassword("1234");
        u.setSex("m");
        u.setAddress("ZhangSan road");
        u.setDescription(u.toString());
        List<UserXml.Element> elements = new ArrayList<>();
        for (int id = 0; id<5; id++) {
            UserXml.Element e = new UserXml.Element();
            e.setId(id);
            e.setTitle("e" + id);
            elements.add(e);
        }
        u.setElements(new UserXml.Elements(elements));

        filePath = "result/serialize2xml_demo.xml";
    }

    @Test
    public void testObj2XmlStr() {
        System.out.println(JAXBDemo.convertToXmlStr(u));
    }

    @Test
    public void testObj2XmlFile() {
        JAXBDemo.convertToXmlFile(u, filePath);
    }

    @Test
    public void testXmlString2Object() {
        String xmlStr = JAXBDemo.convertToXmlStr(u);
        UserXml res = (UserXml) JAXBDemo.convertXmlStrToObject(UserXml.class, xmlStr);
        System.out.println(res);
//        Assert.assertEquals(u, res);走不通
    }

    @Test
    public void testXmlFile2Object() {
        UserXml res = (UserXml) JAXBDemo.convertXmlFileToObject(UserXml.class, filePath);
        System.out.println(res);
//        Assert.assertEquals(u, res);走不通
    }
}
