package by.dima.model.data.command.model;

import by.dima.model.common.AnswerDTO;
import by.dima.model.common.CommandDTO;
import by.dima.model.data.CollectionController;
import by.dima.model.data.UsersCollectionController;
import by.dima.model.data.command.model.impl.*;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.services.files.io.ScannerWrapper;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import by.dima.model.data.services.generate.id.IdGenerateble;
import by.dima.model.data.services.util.GetSecondArgFromArgsUtil;
import lombok.Getter;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Данный класс управляет всеми командами через реализацию паттерна команда
 *
 * @see Command
 */
public class CommandManager {
    @Getter
    private final Map<String, Command> commandMap = new HashMap<>();
    private final LinkedList<String> historyCommandQueue;
    private final Logger logger;

    public CommandManager(Logger logger, UsersCollectionController usersCollectionController, ScannerWrapper scannerWrapper, ParserFromJson<Route> parserFromJsonRoute) {
        this.historyCommandQueue = new LinkedList<>();
        this.logger = logger;

        //TODO: доделать RouteBuilder (routeCreator)
        Command helpCommand = new HelpCommand(this);
        Command infoCommand = new InfoCommand(usersCollectionController);
        Command showCommand = new ShowCommand(usersCollectionController);
        Command updateCommand = new UpdateCommand(parserFromJsonRoute ,usersCollectionController);
        Command clearCommand = new ClearCommand(usersCollectionController);
        Command insertCommand = new InsertCommand(usersCollectionController, parserFromJsonRoute, logger);
        Command exitCommand = new ExitCommand(scannerWrapper);
        Command removeKeyCommand = new RemoveKeyCommand(usersCollectionController);
        Command historyCommand = new HistoryCommand(historyCommandQueue);
        Command executeScriptCommand = new ExecuteScriptCommand(this);
//        Command replaceIfLoweCommand = new ReplaceIfLoweCommand(idGenerateble, collectionController, routeCreator);
//        Command removeLowerKeyCommand = new RemoveLowerKeyCommand(collectionController);
//        Command groupCountingByIdCommand = new GroupCountingByIdCommand(collectionController);
        Command printAscendingCommand = new PrintAscendingCommand(usersCollectionController);
//        Command printFieldDescendingDistanceCommand = new PrintFieldDescendingDistanceCommand(collectionController);
//        Command addCommand = new AddCommand(collectionController, idGenerateble);

        commandMap.put(helpCommand.getKey(), helpCommand);
        commandMap.put(infoCommand.getKey(), infoCommand);
        commandMap.put(showCommand.getKey(), showCommand);
        commandMap.put(insertCommand.getKey(), insertCommand);
        commandMap.put(updateCommand.getKey(), updateCommand);
        commandMap.put(clearCommand.getKey(), clearCommand);
        commandMap.put(removeKeyCommand.getKey(), removeKeyCommand);
        commandMap.put(exitCommand.getKey(), exitCommand);
        commandMap.put(historyCommand.getKey(), historyCommand);
        commandMap.put(executeScriptCommand.getKey(), executeScriptCommand);
//        commandMap.put(replaceIfLoweCommand.getKey(), replaceIfLoweCommand);
//        commandMap.put(removeLowerKeyCommand.getKey(), removeLowerKeyCommand);
//        commandMap.put(groupCountingByIdCommand.getKey(), groupCountingByIdCommand);
        commandMap.put(printAscendingCommand.getKey(), printAscendingCommand);
//        commandMap.put(printFieldDescendingDistanceCommand.getKey(), printFieldDescendingDistanceCommand);
//        commandMap.put(addCommand.getKey(), addCommand);
    }
}
