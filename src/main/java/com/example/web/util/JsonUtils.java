package com.example.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
    
    public static JsonNode parseJson(String json) throws IOException {
        return objectMapper.readTree(json);
    }
}
