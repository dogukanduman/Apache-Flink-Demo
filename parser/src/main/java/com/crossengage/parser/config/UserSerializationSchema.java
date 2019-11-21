package com.crossengage.parser.config;

/**
 * Created by Dogukan Duman on 11.11.2018.
 */

import com.crossengage.parser.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.api.common.serialization.SerializationSchema;

public class UserSerializationSchema implements SerializationSchema<User> {

    static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public byte[] serialize(User user) {
        if(objectMapper == null) {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        }
        try {
            String json = objectMapper.writeValueAsString(user);
            return json.getBytes();
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {

        }
        return new byte[0];
    }
}