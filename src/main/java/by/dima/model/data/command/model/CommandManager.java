package by.dima.model.data.command.model;

import by.dima.model.common.AnswerDTO;
import by.dima.model.data.CollectionController;
import by.dima.model.data.command.model.impl.*;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.services.files.io.ScannerWrapper;
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
    private final ScannerWrapper scannerWrapper;
    private final LinkedList<String> historyCommandQueue;
    @Getter
    private final CollectionController collectionController;
    private final Logger logger;

    public CommandManager(Logger logger, CollectionController collectionController, ScannerWrapper scannerWrapper, IdGenerateble idGenerateble) {
        this.scannerWrapper = scannerWrapper;
        this.historyCommandQueue = new LinkedList<>();
        this.collectionController = collectionController;
        this.logger = logger;

        //TODO: доделать RouteBuilder (routeCreator)
        Command helpCommand = new HelpCommand(this);
        Command infoCommand = new InfoCommand(collectionController);
        Command showCommand = new ShowCommand(collectionController);
//        Command insertCommand = new InsertCommand(collectionController, parserToJson, idGenerateble, routeCreator);
//        Command updateCommand = new UpdateCommand(routeCreator, collectionController);
        Command clearCommand = new ClearCommand(collectionController);
        Command saveCommand = new SaveCommand(collectionController);
        Command exitCommand = new ExitCommand(scannerWrapper);
        Command removeKeyCommand = new RemoveKeyCommand(collectionController);
        Command historyCommand = new HistoryCommand(historyCommandQueue);
        Command executeScriptCommand = new ExecuteScriptCommand(this);
//        Command replaceIfLoweCommand = new ReplaceIfLoweCommand(idGenerateble, collectionController, routeCreator);
        Command removeLowerKeyCommand = new RemoveLowerKeyCommand(collectionController);
        Command groupCountingByIdCommand = new GroupCountingByIdCommand(collectionController);
        Command printAscendingCommand = new PrintAscendingCommand(collectionController);
        Command printFieldDescendingDistanceCommand = new PrintFieldDescendingDistanceCommand(collectionController);
        Command addCommand = new AddCommand(collectionController, idGenerateble);

        commandMap.put(helpCommand.getKey(), helpCommand);
        commandMap.put(infoCommand.getKey(), infoCommand);
        commandMap.put(showCommand.getKey(), showCommand);
//        commandMap.put(insertCommand.getKey(), insertCommand);
//        commandMap.put(updateCommand.getKey(), updateCommand);
        commandMap.put(clearCommand.getKey(), clearCommand);
        commandMap.put(removeKeyCommand.getKey(), removeKeyCommand);
        commandMap.put(exitCommand.getKey(), exitCommand);
        commandMap.put(saveCommand.getKey(), saveCommand);
        commandMap.put(historyCommand.getKey(), historyCommand);
        commandMap.put(executeScriptCommand.getKey(), executeScriptCommand);
//        commandMap.put(replaceIfLoweCommand.getKey(), replaceIfLoweCommand);
        commandMap.put(removeLowerKeyCommand.getKey(), removeLowerKeyCommand);
        commandMap.put(groupCountingByIdCommand.getKey(), groupCountingByIdCommand);
        commandMap.put(printAscendingCommand.getKey(), printAscendingCommand);
        commandMap.put(printFieldDescendingDistanceCommand.getKey(), printFieldDescendingDistanceCommand);
        commandMap.put(addCommand.getKey(), addCommand);

    }

    /**
     * Данный метод позволяет запустить команду из структуры Map, в которой хранятся пара
     * {@link Long} и {@link Command}. Ключ получается с ввода в консоли через класс {@link ScannerWrapper}
     */
    public void executeCommand() {
        String[] arrArgs = scannerWrapper.newLineSplitSpace();
        // запрос новой строки в виде массива строк разделенных по пробелу

        List<String> args = new ArrayList<>(List.of(arrArgs));
        // переделываем в динамический массив

        try {
            String key = args.get(0);
            // получение ключа, то есть первого аргумента в строке

            Command thisCommand = commandMap.get(key);
            // получаем команду из структуры Map<String, Command

            thisCommand.setArgs(GetSecondArgFromArgsUtil.getSecondArg(arrArgs));
            // вызываем метод setArgs у команды, которая реализована по умолчанию,
            // как ничего не делающая, но в некоторых командах insert {id} update {id} и тд она
            // передает id элемента

            thisCommand.execute();
            // запускаем выполенение команды

            if (!(thisCommand instanceof HistoryCommand)) historyCommandQueue.addLast(key);
            if (historyCommandQueue.size() > 8) {
                historyCommandQueue.removeFirst();
            }

        } catch (NullPointerException e) {
            System.err.println("Incorrect command or you dont write any args!" + " NULL POINTER");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Incorrect command or you dont write any args!" + " ARRAY BOUNDS");
        }
    }

    public AnswerDTO executeCommand(String keyCommand) {
        AnswerDTO answerDTO = new AnswerDTO();
        if (commandMap.containsKey(keyCommand)) {
            Command command = commandMap.get(keyCommand);
            command.execute();
            answerDTO.setAnswer(command.getAnswer());
            logger.log(Level.CONFIG, "Ключ " + keyCommand + " существует");
        } else {
            logger.log(Level.WARNING, "Ключ " + keyCommand + " не существует");
        }
        return answerDTO;
    }
}
