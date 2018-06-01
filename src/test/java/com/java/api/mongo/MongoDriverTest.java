package com.java.api.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.Arrays;

public class MongoDriverTest {
    final String collection = "mongo-driver-collection";

    private MongoDatabase connect() {
        MongoCredential mongoCredential = MongoCredential.createCredential("kevin", "api4j",
                "1234".toCharArray());

        MongoClient client = new MongoClient(new ServerAddress("127.0.0.1", 27017), Arrays.asList(mongoCredential));

        MongoDatabase db = client.getDatabase("api4j");
        System.out.printf("connect 2 %s succ.\n", db.getName());
        return db;
    }

    @Test
    public void addCollection() {
        MongoDatabase db = connect();
        db.createCollection(collection);
        System.out.printf("create %s succ.\n", collection);
    }

    @Test
    public void getCollection() {
        MongoDatabase db = connect();
        MongoCollection<Document> docs = db.getCollection(collection);
        System.out.printf("docs count in %s: %s\n", collection, docs.count());
    }

    /**
     * MongoCollection.insert()
     */
    @Test
    public void addDocument() {
        MongoDatabase db = connect();
        MongoCollection<Document> docs = db.getCollection(collection);
        Document doc = new Document("title", "MongoDB").
                append("description", "database").
                append("likes", 100).
                append("by", "Fly");
        docs.insertOne(doc);
        System.out.println("文档插入成功");
    }

    /**
     * MongoCollection.find()
     */
    @Test
    public void getDocument() {
        MongoDatabase db = connect();
        MongoCollection<Document> mCollection = db.getCollection(collection);
        FindIterable<Document> docs = mCollection.find();
        System.out.println("iterate by iterable:");
        for (Document doc : docs) {
            System.out.println(doc.get("_id"));
        }
        MongoCursor<Document> docCursor = docs.iterator();
        System.out.println("iterate by mongoCursor(iterator):");
        while (docCursor.hasNext()) {
            System.out.println(docCursor.next().get("_id"));
        }
    }

    /**
     * MongoCollection.update()
     */
    @Test
    public void updateDoc() {
        MongoDatabase db = connect();
        MongoCollection<Document> c = db.getCollection(collection);
        Bson filters = Filters.eq("_id", new ObjectId("5b10ee1c6d559d05c5345b2e"));
        Bson update = new Document("$set", new Document("by", "run"));
        UpdateResult r = c.updateMany(filters, update);
        System.out.println("update count:" + r.getModifiedCount());
    }

    /**
     * MongoCollection.delete()
     */
    @Test
    public void deleteDoc() {
        MongoDatabase db = connect();
        MongoCollection<Document> c = db.getCollection(collection);
//        Bson filter = Filters.eq("_id", "5b10e9a96d559d059f85039a");
        Bson filter = Filters.eq("_id", new ObjectId("5b10e9a96d559d059f85039a"));
        DeleteResult r = c.deleteOne(filter);
        System.out.println("delete count:" + r.getDeletedCount());
    }
}
