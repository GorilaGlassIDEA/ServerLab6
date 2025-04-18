package by.dima.model.common;

import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class CommandDTOWrapper {
    private String commandName;
    private String commandArg;
    private String stringRoute;
    // прописать DI в Spring внедрив в parser Logger
    @Setter
    private ParserFromJson<Route> parser;

    public CommandDTOWrapper(CommandDTO commandDTO) {
        this.commandName = commandDTO.getNameCommand();
        this.commandArg = commandDTO.getArgCommand();
        this.stringRoute = commandDTO.getJsonRouteObj();
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
