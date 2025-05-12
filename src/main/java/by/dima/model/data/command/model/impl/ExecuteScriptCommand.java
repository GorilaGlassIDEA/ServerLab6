package by.dima.model.data.command.model.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.common.ExecuteDTO;
import by.dima.model.common.route.main.Route;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.services.builder.RouteBuilder;
import by.dima.model.data.services.files.parser.string.model.ParserFromJson;
import by.dima.model.data.services.files.parser.string.model.ParserToJson;
import by.dima.model.data.services.iterator.TextIterable;
import by.dima.model.server.request.parser.RouteParserFromJson;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;


/**
 * Данная команда позволяет запускать другие команды с помощью текстовых файлов, содержащих последовательность
 * команд. Для правильного чтения данных из файла используется собственный класс-итератор {@link TextIterable}
 */
@Setter
@Getter
public class ExecuteScriptCommand extends CommandAbstract {
    private final CommandManager manager;
    private ExecuteDTO executeDTO;
    private RouteBuilder builder;
    private ParserToJson<Route> parser;
    private StringBuilder stringBuilder;

    public ExecuteScriptCommand(ParserToJson<Route> parser, CommandManager manager) {
        super("execute_script", "Execute commands from a specified file.");
        this.manager = manager;
        this.parser = parser;
    }

    @Override
    public void execute() {
        stringBuilder = new StringBuilder();
        executeDTO = getCommandDTO().getExecuteDTO();
        try {
            Map<String, List<Route>> executeCommandMap = getCommandDTO().getExecuteDTO().getCommandsForExecuteScript();

            for (String s : executeCommandMap.keySet()) {
                Command command = manager.getCommand(s);
                if (command != null) {
                    CommandDTO commandDTO = new CommandDTO();
                    commandDTO.setNameCommand(command.getKey());
                    commandDTO.setUserID(getCommandDTO().getUserID());
                    for (Route route : executeCommandMap.get(command.getKey())) {
                        commandDTO.setJsonRouteObj(parser.getJson(route));
                        command.setCommandDTO(commandDTO);
                        command.execute();
                        if (!command.getKey().equals("execute_script"))
                            stringBuilder.append("Команда ").append(command.getKey()).append(" выполнена!").append("\n");
                    }
                } else {
                    stringBuilder.append("Не удалось выполнить команду: ").append(s).append("\n");
                }
            }
        } catch (RuntimeException e) {
            stringBuilder.append("Не удалось выполнить команду").append("\n");
        }
    }

    @Override
    public String getAnswer() {
        return new String(stringBuilder).strip();
    }

}
