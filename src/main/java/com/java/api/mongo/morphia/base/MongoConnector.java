package com.java.api.mongo.morphia.base;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MongoConnector {
    private static String db;
    private static Morphia morphia;
    private static List<ServerAddress> seeds = new ArrayList<>(1);
    private static List<MongoCredential> credentials = new ArrayList<>(1);
    private static MongoClientOptions mongoOptions;

    static {
        Properties prop = new Properties();
        try {
            prop.load(MongoConnector.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("fail load config.properties", e);
        }
        db = prop.getProperty("mongo.db");

        seeds.add(new ServerAddress(prop.getProperty("mongo.host"), Integer.valueOf(prop.getProperty("mongo.port"))));

        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        if (prop.getProperty("mongo.sockettimeout") != null) {
            builder.socketTimeout(Integer.valueOf(prop.getProperty("mongo.sockettimeout")));
        }
        if (prop.getProperty("mongo.connecttimeout") != null) {
            builder.connectTimeout(Integer.valueOf(prop.getProperty("mongo.connecttimeout")));
        }
        if (prop.getProperty("mongo.maxconnectidletime") != null) {
            builder.maxConnectionIdleTime(Integer.valueOf(prop.getProperty("mongo.maxconnectidletime")));
        }
        mongoOptions = builder.build();

        String user = prop.getProperty("mongo.user");
        String pwd = prop.getProperty("mongo.pwd");
        String db = prop.getProperty("mongo.db");
        credentials.add(MongoCredential.createScramSha1Credential(user, db, pwd.toCharArray()));

        morphia = new Morphia();
        morphia.mapPackage(prop.getProperty("mongo.morphia.map.package"));
    }


    public static Datastore getDs() {
        MongoClient mongoClient = new MongoClient(seeds, credentials, mongoOptions);
        Datastore ds = morphia.createDatastore(mongoClient, db);
        ds.ensureIndexes();
        return ds;
    }
}
