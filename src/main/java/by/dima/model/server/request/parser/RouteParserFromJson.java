package by.dima.model.server.request.parser;

import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Setter
public class RouteParserFromJson implements ParserFromJson<Route> {
    // Переписать под Spring DI
    private final ObjectMapper mapper;

    public RouteParserFromJson(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Route getModels(String jsonContent) {
        try {
            return mapper.readValue(jsonContent, Route.class);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
