package com.java.api.serialize;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NativeSerializeTest {
    NativeSerializeDemo demo;
    UserSerializable u;
    String outputPath;

    @Before
    public void prepare() {
        demo = new NativeSerializeDemo<UserSerializable>();

        u = new UserSerializable();
        u.setName("ZhangSan");
        u.setAge(18);
        u.setPhone("136");
        u.setPassword("1234");
        u.setSex("m");
        u.setAddress("ZhangSan road");
        u.setDescription(u.toString());

        outputPath = "result/user_serialize.txt";
    }

    @Test
    public void testSerialize() throws Exception {
        for(int i = 1; i<=100; i++) {
            demo.serialize(u, outputPath);
        }
    }

    @Test
    public void testDeserialize() throws Exception {
        for(int i = 1; i<=100; i++) {
            UserSerializable out = (UserSerializable) demo.deserialize(outputPath);
            Assert.assertEquals(u, out);
            System.out.println("round" + i + ":" + out);
        }
    }
}
