package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.data.abstracts.model.Models;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class ParserFromJsonJacksonImpl implements ParserFromJson<Models> {

    ObjectMapper mapper;

    public ParserFromJsonJacksonImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Models getModels(String jsonContent) {
        try {
            return mapper.readValue(jsonContent, Models.class);
        } catch (JsonProcessingException e) {
            return new Models(new HashMap<>());
        }
    }
}
