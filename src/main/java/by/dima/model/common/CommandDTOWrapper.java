package by.dima.model.common;

import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.server.request.parser.RouteParserFromJson;

public class CommandDTOWrapper {
    private final String commandName;
    private final String commandArg;
    private final String stringRoute;
    private final ParserFromJson<Route> parser;

    public CommandDTOWrapper(CommandDTO commandDTO) {
        this.commandName = commandDTO.getNameCommand();
        this.commandArg = commandDTO.getArgCommand();
        this.stringRoute = commandDTO.getJsonRouteObj();
        parser = new RouteParserFromJson();
    }

    public String getNameCommand() {
        if (commandName != null && !commandName.isEmpty() && !commandName.isBlank()) {
            return commandName.strip();
        } else return null;
    }

    public String getArg() {
        if (commandArg != null && !commandArg.isEmpty() && !commandArg.isBlank()) {
            return commandArg.strip();
        } else return null;
    }

    public Route getRoute() {
        return parser.getModels(stringRoute);
    }

}
