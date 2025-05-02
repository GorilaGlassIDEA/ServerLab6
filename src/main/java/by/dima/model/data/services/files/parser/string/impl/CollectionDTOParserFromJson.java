package by.dima.model.data.services.files.parser.string.impl;

import by.dima.model.data.abstracts.model.CollectionDTO;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.util.HashMap;
@NoArgsConstructor
public class CollectionDTOParserFromJson implements ParserFromJson<CollectionDTO> {

    private ObjectMapper mapper;

    public CollectionDTOParserFromJson(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CollectionDTO getModels(String jsonContent) {
        try {
            return mapper.readValue(jsonContent, CollectionDTO.class);
        } catch (JsonProcessingException e) {
            return new CollectionDTO(new HashMap<>());
        }
    }
}
