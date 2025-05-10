package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.data.services.files.parser.string.exceptions.JsonException;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ParserToJsonImpl<T> implements ParserToJson<T> {
    ObjectMapper objectMapper;

    //Dependency injection
    public ParserToJsonImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String getJson(T models) {
        try {
            return objectMapper.writeValueAsString(models);
        } catch (JsonProcessingException e) {
            throw new JsonException("Ошибка сериализации объекта", e);
        }
    }
}
