package com.java.api.mongo.springdata;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/applicationContext.xml"
})
public class MongoRepositoryTest {
    final String id = "id_test";
    final String name = "name_test";

    @Autowired
    private DocumentDemoRepository repository;

    @Test
    public void findByName() {
        DocumentDemo doc = repository.findByName(name);
        System.out.println(doc);
    }

    @Test
    public void save() {
        DocumentDemo doc = new DocumentDemo();
        doc.setId(id);
        doc.setName(name);
        doc.setUpdateTs(System.currentTimeMillis());
        repository.save(doc);
    }
}
