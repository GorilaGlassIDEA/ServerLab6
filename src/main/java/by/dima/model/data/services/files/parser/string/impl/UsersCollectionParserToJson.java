package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.data.abstracts.model.UsersCollectionDTO;
import by.dima.model.data.services.files.parser.string.exceptions.JsonException;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class UsersCollectionParserToJson implements ParserToJson<UsersCollectionDTO> {
    private final ObjectMapper mapper;

    public UsersCollectionParserToJson(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public String getJson(UsersCollectionDTO models) {
        try {
            return mapper.writeValueAsString(models);
        } catch (JsonProcessingException e) {
            throw new JsonException("Ошибка сериализации объекта", e);
        }
    }
}
