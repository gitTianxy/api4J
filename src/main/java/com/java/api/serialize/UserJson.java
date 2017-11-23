package com.java.api.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.IOException;

/**
 * POINTS:
 * 1. password序列化前加密, 通过重写定制readObject()和writeObject()方法
 * 2. description字段为transient
 * 3. 定义compareTo方法用于比较原对象和反序列化生成的对象
 */

@Data
public class UserJson {
    private String name;
    private int age;
    private String sex;
    private String address;
    @JsonProperty("passwd")
    @JsonSerialize(using = PWDSerializer.class)
    @JsonDeserialize(using = PWDDeserializer.class)
    private String password;
    private String phone;
    @JsonIgnore
    private String description;

    @Override
    public String toString() {
        return name + "{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public boolean compareTo(UserJson u) {
        return (name == u.getName()) && (phone == u.getPhone());
    }

    static class PWDSerializer extends JsonSerializer<String> {

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(Integer.valueOf(Integer.valueOf(value).intValue() << 2).toString());
        }
    }

    static class PWDDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String value = p.getValueAsString();
            return Integer.valueOf(Integer.valueOf(value).intValue() >> 2).toString();
        }
    }
}
