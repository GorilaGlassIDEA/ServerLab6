package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.services.files.parser.string.exceptions.JsonException;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CollectionDTOParserToJson implements ParserToJson<CollectionDTO> {
    ObjectMapper objectMapper;

    //Dependency injection
    public CollectionDTOParserToJson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public String getJson(CollectionDTO models) {
        try {
            return objectMapper.writeValueAsString(models);
        } catch (JsonProcessingException e) {
            throw new JsonException("Ошибка сериализации объекта", e);
        }
    }
}
