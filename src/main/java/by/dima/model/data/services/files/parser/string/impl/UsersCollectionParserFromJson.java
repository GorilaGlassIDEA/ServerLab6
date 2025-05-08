package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.data.abstracts.model.UsersCollectionDTO;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersCollectionParserFromJson implements ParserFromJson<UsersCollectionDTO> {
    private final ObjectMapper mapper;
    private final Logger logger;

    public UsersCollectionParserFromJson(ObjectMapper mapper, Logger logger) {
        this.mapper = mapper;
        this.logger = logger;
    }

    @Override
    public UsersCollectionDTO getModels(String jsonContent) {
        try {
            return mapper.readValue(jsonContent, UsersCollectionDTO.class);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Не удалось переделать json в UsersCollectionDTO!");
            return new UsersCollectionDTO(new HashMap<>());
        }
    }
}
