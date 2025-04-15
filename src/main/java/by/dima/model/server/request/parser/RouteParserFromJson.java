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
@NoArgsConstructor
public class RouteParserFromJson implements ParserFromJson<Route> {
    // Переписать под Spring DI
//    private ObjectMapper mapper;
    private Logger logger;

    @Override
    public Route getModels(String jsonContent) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonContent, Route.class);
        } catch (JacksonException e) {
            logger.log(Level.WARNING, "JacksonException: " + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
}
