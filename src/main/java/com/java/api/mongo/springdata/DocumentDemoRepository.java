package com.java.api.mongo.springdata;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentDemoRepository extends MongoRepository<DocumentDemo, String> {
    public DocumentDemo findByName(String name);
}
