package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.data.abstracts.model.Models;
import by.dima.model.data.services.files.parser.string.exceptions.JsonException;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ParserToJsonJacksonImpl implements ParserToJson {
    ObjectMapper objectMapper;

    //Dependency injection
    public ParserToJsonJacksonImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }

    @Override
    public String getJson(Models models) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(models);
        } catch (JsonProcessingException e) {
            throw new JsonException("Ошибка сериализации объекта", e);
        }
    }
}
