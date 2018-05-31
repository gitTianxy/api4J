package com.java.api.mongo.morphia.mao;

import com.java.api.mongo.morphia.entity.EntityDemo;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityDemoMAO extends BasicDAO<EntityDemo, ObjectId> {

    @Autowired
    public EntityDemoMAO(Datastore api4jDs) {
        super(EntityDemo.class, api4jDs);
    }
}
