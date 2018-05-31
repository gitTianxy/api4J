package com.java.api.mongo;

import com.java.api.mongo.morphia.entity.EntityDemo;
import com.java.api.mongo.morphia.mao.EntityDemoMAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/applicationContext.xml"
})
public class EntityDemoMaoTest {
    @Autowired
    private EntityDemoMAO entityDemoMAO;

    @Test
    public void save() {
        EntityDemo entity = new EntityDemo();
        entity.setUniqField("uniq_field");
        entity.setGeneralField("general_field");
        entityDemoMAO.save(entity);
    }

    @Test
    public void find() {
        EntityDemo demo = entityDemoMAO.findOne("uniqField", "uniq_field");
        System.out.println(demo.getUniqField());
    }

    @Test
    public void delete() {
        Query<EntityDemo> q = entityDemoMAO.createQuery();
        q.field("uniqField").equal("uniq_field");
        entityDemoMAO.deleteByQuery(q);
    }
}
