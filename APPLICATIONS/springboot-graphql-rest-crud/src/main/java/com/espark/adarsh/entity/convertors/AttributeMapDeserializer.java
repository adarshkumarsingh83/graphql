package com.espark.adarsh.entity.convertors;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class AttributeMapDeserializer extends StdDeserializer<Map<String, String>> {

    @Autowired
    ObjectMapper objectMapper;

    public AttributeMapDeserializer() {
        this(null);
    }

    protected AttributeMapDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public Map<String, String> deserialize(JsonParser jsonparser, DeserializationContext ctxt)
            throws IOException, JacksonException {
        String data = jsonparser.getText();

        try {
            return objectMapper.readValue(data.toString()
                    , new TypeReference<Map<String, String>>() {
                    });

        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
