package com.java.api.mongo.morphia.mao;

import com.java.api.mongo.morphia.base.MongoConnector;
import com.java.api.mongo.morphia.entity.EntityDemo;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

public class EntityDemoMAO extends BasicDAO<EntityDemo, ObjectId> {

    public EntityDemoMAO() {
        super(MongoConnector.getDs());
    }
}
