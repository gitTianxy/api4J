package com.java.api.mongo.morphia.base;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MongoDSManager extends AbstractFactoryBean<Datastore> {

    // mongodb config
    private String host;
    private Integer port;
    private String db;
    private String user;
    private String pwd;
    private Integer socketTimeout;
    private Integer connectTimeout;
    private Integer maxConnectIdleTime;

    // morphia config
    private String[] mapPackages;
    private String[] mapClasses;


    @Nullable
    @Override
    public Class<Datastore> getObjectType() {
        return Datastore.class;
    }

    @Override
    protected Datastore createInstance() throws Exception {
        // seeds
        ServerAddress address = new ServerAddress(host, port);
        // clientOptions
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        if (socketTimeout != null) {
            builder.socketTimeout(socketTimeout);
        }
        if (connectTimeout != null) {
            builder.connectTimeout(connectTimeout);
        }
        if (maxConnectIdleTime != null) {
            builder.maxConnectionIdleTime(maxConnectIdleTime);
        }
        MongoClientOptions clientOptions = builder.build();
        // credentials
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createScramSha1Credential(user, db, pwd.toCharArray()));
        // client
        MongoClient mongoClient = new MongoClient(address, credentials, clientOptions);
        // morphia
        Morphia morphia = new Morphia();
        if (mapPackages != null) {
            for (String pkg : mapPackages) {
                morphia.mapPackage(pkg);
            }
        }
        if (mapClasses != null) {
            for (String cls : mapClasses) {
                morphia.map(Class.forName(cls));
            }
        }
        // ds
        Datastore ds = morphia.createDatastore(mongoClient, db);
        ds.ensureIndexes();
        return ds;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getMaxConnectIdleTime() {
        return maxConnectIdleTime;
    }

    public void setMaxConnectIdleTime(Integer maxConnectIdleTime) {
        this.maxConnectIdleTime = maxConnectIdleTime;
    }

    public String[] getMapPackages() {
        return mapPackages;
    }

    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }

    public String[] getMapClasses() {
        return mapClasses;
    }

    public void setMapClasses(String[] mapClasses) {
        this.mapClasses = mapClasses;
    }
}
