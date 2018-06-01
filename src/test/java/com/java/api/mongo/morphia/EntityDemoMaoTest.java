package com.java.api.mongo.morphia;

import com.java.api.mongo.morphia.entity.EntityDemo;
import com.java.api.mongo.morphia.mao.EntityDemoMAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 针对DefaultServerMonitor会注册连接`common`数据库, 所以做一个创建和注册操作
 * > use common;
 * switched to db common
 * > db.createUser({user:'kevin',pwd:'1234',roles:['read']});
 * Successfully added user: { "user" : "kevin", "roles" : [ "read" ] }
 */
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
