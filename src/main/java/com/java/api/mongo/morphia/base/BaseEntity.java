package com.java.api.mongo.morphia.base;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

public abstract class BaseEntity {
    @Id
    @Property("id")
    protected ObjectId id;
    @Property("update_ts")
    protected long updateTs;

    @PrePersist
    void prePersist() {
        updateTs = System.currentTimeMillis();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Date getUpdateTs() {
        return new Date(updateTs);
    }

    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs.getTime();
    }
}
