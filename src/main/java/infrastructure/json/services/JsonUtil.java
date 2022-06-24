package infrastructure.json.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonUtil {
    <T> T fromJson(String content, Class<T> valueType) throws JsonProcessingException;
    String toJson(Object value) throws JsonProcessingException;
}
