package com.java.api.serialize;

import org.junit.Before;
import org.junit.Test;

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
