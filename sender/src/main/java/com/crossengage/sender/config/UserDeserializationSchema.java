package com.crossengage.sender.config;

/**
 * Created by Dogukan Duman on 11.11.2018.
 */

import com.crossengage.sender.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class UserDeserializationSchema implements DeserializationSchema<User> {

    static ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public User deserialize(byte[] bytes) throws IOException {

        return objectMapper.readValue(bytes, User.class);
    }

    @Override
    public boolean isEndOfStream(User user) {
        return false;
    }

    @Override
    public TypeInformation<User> getProducedType() {
        return TypeInformation.of(User.class);
    }
}