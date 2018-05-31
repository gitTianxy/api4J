package com.java.api.mongo.morphia.entity;

import com.java.api.mongo.morphia.base.BaseEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

@Entity(value = "entity_demo", noClassnameStored = true)
public class EntityDemo extends BaseEntity {
    @Indexed(name = "idx_uniq", unique = true)
    private String uniqField;
    private String generalField;

    public String getUniqField() {
        return uniqField;
    }

    public void setUniqField(String uniqField) {
        this.uniqField = uniqField;
    }

    public String getGeneralField() {
        return generalField;
    }

    public void setGeneralField(String generalField) {
        this.generalField = generalField;
    }
}
