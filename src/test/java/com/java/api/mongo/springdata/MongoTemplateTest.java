package com.java.api.mongo.springdata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/applicationContext.xml"
})
public class MongoTemplateTest {
    final String id = "id_test";
    final String name = "name_test";
    final DocumentDemo doc = new DocumentDemo();

    @Before
    public void before() {
        doc.setId(id);
        doc.setName(name);
        doc.setUpdateTs(System.currentTimeMillis());
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void save() {
        mongoTemplate.save(doc);
    }

    @Test
    public void find() {
        DocumentDemo demo = mongoTemplate.findById(id, DocumentDemo.class);
        System.out.println(demo);
    }

    @Test
    public void delete() {
        mongoTemplate.remove(doc);
    }
}
