package by.dima.model.data.command.model.impl;

import by.dima.model.common.CommandDTO;
import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;
import by.dima.model.data.services.files.io.read.ReadFileFiles;
import by.dima.model.data.services.files.io.read.ReadableFile;
import by.dima.model.data.services.iterator.TextIterable;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;

/**
 * Данная команда позволяет запускать другие команды с помощью текстовых файлов, содержащих последовательность
 * команд. Для правильного чтения данных из файла используется собственный класс-итератор {@link TextIterable}
 */
@Setter
@Getter
public class ExecuteScriptCommand extends CommandAbstract {
    private final CommandManager manager;
    private String content;
    private TextIterable textIterable;
    private StringBuilder builder;


    public ExecuteScriptCommand(CommandManager manager) {
        super("execute_script", "Execute commands from a specified file.");
        this.manager = manager;
        builder = new StringBuilder();
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        String allCommand = getCommandDTO().getArgCommand();
        CommandDTO thisCommandDTO;
        for (String command : allCommand.split(" ")) {
            try {
                thisCommandDTO = new CommandDTO();
                thisCommandDTO.setUserID(getCommandDTO().getUserID());
                thisCommandDTO.setNameCommand(command.strip());
                Command thisCommand = manager.execute(command, thisCommandDTO);
                builder.append("Команда выполнена успешно: ").append(command).append("\n");
                builder.append("Ответ: ").append(thisCommand.getAnswer());
            } catch (RuntimeException e) {
                e.printStackTrace();
                builder.append("Не удалось выполнить команду: ").append(command).append("\n");
            }
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder).strip();
    }

}
