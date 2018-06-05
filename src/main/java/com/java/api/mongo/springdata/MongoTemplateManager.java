package com.java.api.mongo.springdata;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.lang.Nullable;

public class MongoTemplateManager extends AbstractFactoryBean<MongoTemplate> {

    // mongodb config
    private String mongoUri;
    private Integer socketTimeout;
    private Integer connectTimeout;
    private Integer maxConnectIdleTime;
    // spring data config


    @Nullable
    @Override
    public Class<MongoTemplate> getObjectType() {
        return MongoTemplate.class;
    }

    @Override
    protected MongoTemplate createInstance() throws Exception {
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
        MongoClientURI uri = new MongoClientURI(mongoUri, builder);
        MongoDbFactory factory = new SimpleMongoDbFactory(uri);
        MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(factory, new MongoMappingContext());
        mappingConverter.setTypeMapper(typeMapper);
        return new MongoTemplate(factory, mappingConverter);
    }

    public void setMongoUri(String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setMaxConnectIdleTime(Integer maxConnectIdleTime) {
        this.maxConnectIdleTime = maxConnectIdleTime;
    }
}
