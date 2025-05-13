package com.app.flexcart.flexcart.backend.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ParameterParser {
    private final ObjectMapper objectMapper;

    public ParameterParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> parse(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

    }

    public String convertToJsonString(Map<String, Object> parameters) throws JsonProcessingException {
        return objectMapper.writeValueAsString(parameters);

    }
}