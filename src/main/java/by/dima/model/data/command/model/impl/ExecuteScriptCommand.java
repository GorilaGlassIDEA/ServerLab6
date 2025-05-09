package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.services.builder.RouteBuilder;
import by.dima.model.data.services.iterator.TextIterable;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
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
    private List<Map<String, String>> executeCommandMap;
    private RouteBuilder builder;


    public ExecuteScriptCommand(CommandManager manager) {
        super("execute_script", "Execute commands from a specified file.");
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println(getCommandDTO().getExecuteDTO());
    }

    //Command=insert;
//RouteId=1;
//RouteName=NewRoute;
//CoordinateIntX=10;
//CoordinateDoubleY=1.2;
//LocationFromDoubleX=102.4;
//LocationFromFloatY=12332.3;
//LocationFromString="Saint-Petersburg";
//LocationToDoubleX=123.2;
//LocationToDoubleY=122.4;
//LocationToString="Moscow Red Square";
//Distance=100
    @Override
    public String getAnswer() {
        return "";
    }

}
