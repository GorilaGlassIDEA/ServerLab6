package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.services.iterator.TextIterable;
import lombok.Getter;
import lombok.Setter;


/**
 * Данная команда позволяет запускать другие команды с помощью текстовых файлов, содержащих последовательность
 * команд. Для правильного чтения данных из файла используется собственный класс-итератор {@link TextIterable}
 */
@Setter
@Getter
public class ExecuteScriptCommand extends CommandAbstract {
    private final CommandManager manager;
    private String content;


    public ExecuteScriptCommand(CommandManager manager) {
        super("execute_script", "Execute commands from a specified file.");
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println(getCommandDTO());
    }

    @Override
    public String getAnswer() {
        return "";
    }

}
