package com.java.api.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class JacksonTest {
    JacksonDemo demo;
    JacksonDemo.Container javaObj;
    UserJson u;

    @Before
    public void prepare() {
        demo = new JacksonDemo();
        javaObj = new JacksonDemo.Container();
        List<JacksonDemo.Element> elementList = new ArrayList<>();
        Map<String, JacksonDemo.Element> elementMap = new HashMap<>();
        long idx = 0;
        while (idx < 10) {
            JacksonDemo.Element element = new JacksonDemo.Element(++idx, String.valueOf(idx), new Date());
            elementList.add(element);
            elementMap.put(String.valueOf(idx), element);
        }
        javaObj.setId(0);
        javaObj.setName("javaObj");
        javaObj.setElementList(elementList);
        javaObj.setElementMap(elementMap);
        javaObj.setOthers("others");
        javaObj.setCreateTime(new Date());

        u = new UserJson();
        u.setName("ZhangSan");
        u.setAge(18);
        u.setPhone("136");
        u.setPassword("1234");
        u.setSex("m");
        u.setAddress("ZhangSan road");
        u.setDescription(u.toString());
    }

    @Test
    public void testObj2Json() throws JsonProcessingException {
        String json = demo.obj2Json(javaObj);
        System.out.println(json);
    }

    @Test
    public void testJson2Obj() throws IOException {
        String json = demo.obj2Json(javaObj);
        System.out.println("***json 2 object: ");
        JacksonDemo.Container retObj = demo.json2Obj(json, JacksonDemo.Container.class);
        System.out.println(retObj);
        System.out.println("***json 2 object-array: ");
        String elements = demo.obj2Json(javaObj.getElementList());
        for(JacksonDemo.Element e : demo.json2Obj(elements, JacksonDemo.Element[].class)) {
            System.out.println(e);
        }
        System.out.println("***json 2 object-list: ");
        List<JacksonDemo.Element> resList = demo.json2Obj(elements, new TypeReference<List<JacksonDemo.Element>>(){});
        for(JacksonDemo.Element e : resList) {
            System.out.println(e);
        }
    }

    @Test
    public void testJson2JsonNode() throws IOException {
        String json = demo.obj2Json(javaObj);
        JsonNode retNode = demo.json2JsonNode(json);
        Iterator<Map.Entry<String, JsonNode>> fields = retNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> f = fields.next();
            System.out.println(f.getKey() + "=" + f.getValue());
        }
    }

    @Test
    public void testUserJson() throws IOException {
        String json = demo.obj2Json(u);
        System.out.println(json);

        UserJson res = demo.json2Obj(json, UserJson.class);
        System.out.println(res);
    }
}
