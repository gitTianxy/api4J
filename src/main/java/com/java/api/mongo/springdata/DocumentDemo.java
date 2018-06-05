package com.java.api.mongo.springdata;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "springdata_documentdemo")
public class DocumentDemo {
    @Id
    private String id;
    @Indexed(name = "idx_name", unique = true)
    private String name;
    private long updateTs;

    public boolean equals(DocumentDemo doc) {
        return this.id.equals(doc.getId()) && this.name.equals(doc.getName());
    }

    @Override
    public String toString() {
        return String.format("DocumentDemo[id:%s,name:%s,updateTime:%s]", id, name, new Date(updateTs));
    }
}
