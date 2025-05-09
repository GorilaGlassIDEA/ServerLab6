package by.dima.model.common;

import by.dima.model.common.route.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.server.request.parser.RouteParserFromJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class CommandDTOWrapper {
    private final String commandName;
    private final String commandArg;
    private final String stringRoute;
    @Getter
    private final CommandDTO commandDTO;
    private final ParserFromJson<Route> parser;

    public CommandDTOWrapper(CommandDTO commandDTO, ObjectMapper mapper) {
        this.commandName = commandDTO.getNameCommand();
        this.commandArg = commandDTO.getArgCommand();
        this.stringRoute = commandDTO.getJsonRouteObj();
        this.commandDTO = commandDTO;
        parser = new RouteParserFromJson(mapper);
    }

    public String getNameCommand() {
        if (commandName != null && !commandName.isEmpty() && !commandName.isBlank()) {
            return commandName.strip();
        } else return null;
    }

    public Route getRoute() {
        return parser.getModels(stringRoute);
    }

}
