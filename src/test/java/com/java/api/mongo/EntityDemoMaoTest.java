package com.java.api.mongo;

import com.java.api.mongo.morphia.entity.EntityDemo;
import com.java.api.mongo.morphia.mao.EntityDemoMAO;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.query.Query;

public class EntityDemoMaoTest {
    private EntityDemoMAO mao;

    @Before
    public void before() {
        mao = new EntityDemoMAO();
    }

    @Test
    public void save() {
        EntityDemo entity = new EntityDemo();
        entity.setUniqField("uniq_field");
        entity.setGeneralField(0);
        mao.save(entity);
    }

    @Test
    public void find() {
        EntityDemo demo = mao.findOne("uniqField", "uniq_field");
        System.out.println(demo.getUniqField());
    }

    @Test
    public void delete() {
        Query<EntityDemo> q = mao.createQuery();
        q.field("uniqField").equal("uniq_field");
        mao.deleteByQuery(q);
    }
}
