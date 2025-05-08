package by.dima.model.data.command.model;

import by.dima.model.common.CommandDTO;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.impl.*;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.common.main.Route;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import lombok.Getter;

import java.util.*;
import java.util.logging.Logger;

/**
 * Данный класс управляет всеми командами через реализацию паттерна команда
 *
 * @see Command
 */
public class CommandManager {
    @Getter
    private final Map<String, Command> commandMap = new HashMap<>();
    private final Logger logger;
    private final UsersCollectionController usersCollectionController;

    public CommandManager(Logger logger, UsersCollectionController usersCollectionController, ParserFromJson<Route> parserFromJsonRoute) {
        this.logger = logger;
        this.usersCollectionController = usersCollectionController;

        //TODO: доделать RouteBuilder (routeCreator)
        Command helpCommand = new HelpCommand(this);
        Command infoCommand = new InfoCommand(usersCollectionController);
        Command showCommand = new ShowCommand(usersCollectionController);
        Command updateCommand = new UpdateCommand(parserFromJsonRoute, usersCollectionController);
        Command clearCommand = new ClearCommand(usersCollectionController);
        Command insertCommand = new InsertCommand(usersCollectionController, parserFromJsonRoute, logger);
        Command removeKeyCommand = new RemoveKeyCommand(usersCollectionController);
        Command historyCommand = new HistoryCommand(usersCollectionController);
        Command executeScriptCommand = new ExecuteScriptCommand(this);
        Command replaceIfLoweCommand = new ReplaceIfLoweCommand(parserFromJsonRoute, usersCollectionController);
        Command removeLowerKeyCommand = new RemoveLowerKeyCommand(usersCollectionController);
        Command groupCountingByIdCommand = new GroupCountingByIdCommand(usersCollectionController);
        Command printAscendingCommand = new PrintAscendingCommand(usersCollectionController);
        Command printFieldDescendingDistanceCommand = new PrintFieldDescendingDistanceCommand(usersCollectionController);

        commandMap.put(helpCommand.getKey(), helpCommand);
        commandMap.put(infoCommand.getKey(), infoCommand);
        commandMap.put(showCommand.getKey(), showCommand);
        commandMap.put(insertCommand.getKey(), insertCommand);
        commandMap.put(updateCommand.getKey(), updateCommand);
        commandMap.put(clearCommand.getKey(), clearCommand);
        commandMap.put(removeKeyCommand.getKey(), removeKeyCommand);
        commandMap.put(historyCommand.getKey(), historyCommand);
        commandMap.put(executeScriptCommand.getKey(), executeScriptCommand);
        commandMap.put(replaceIfLoweCommand.getKey(), replaceIfLoweCommand);
        commandMap.put(removeLowerKeyCommand.getKey(), removeLowerKeyCommand);
        commandMap.put(groupCountingByIdCommand.getKey(), groupCountingByIdCommand);
        commandMap.put(printAscendingCommand.getKey(), printAscendingCommand);
        commandMap.put(printFieldDescendingDistanceCommand.getKey(), printFieldDescendingDistanceCommand);
    }

    public void execute(Command command) {
        command.execute();
        usersCollectionController.addCommandName(command.getCommandDTO().getNameCommand(), command.getCommandDTO().getUserID());
    }

    public Command execute(String stringCommand, CommandDTO commandDTO) throws RuntimeException {
        Command command = commandMap.get(stringCommand.strip());
        command.setCommandDTO(commandDTO);
        command.execute();
        return command;
    }
}
