package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.CommandManager;
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
public abstract class ExecuteScriptCommand extends CommandAbstract {
    private final CommandManager commandManager;
    private String content;
    private TextIterable textIterable;


    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script", "Execute commands from a specified file.");
        this.commandManager = commandManager;
    }

//    @Override
//    public void setArgs(String arg) {
//        String content = tryReadFile(arg);
//
//        if (content == null) {
//            String relativePath = System.getProperty("user.dir") + File.separator + arg;
//            content = tryReadFile(relativePath);
//        }
//
//        if (content == null) {
//            System.err.println("Не удалось найти или прочитать файл");
//        } else {
//            System.out.println(content);
//            System.out.println("Путь открытия файла: " + arg);
//        }
//
//        this.content = content; // Фиксируем конечное состояние
//        this.textIterable = (content != null) ? new TextIterable(content) : null;
//    }

    @Override
    public void execute() {
        if (content != null) {
            for (String command : textIterable) {
                if (!(command.split(" ").length > 1 || command.equals("insert") || command.equals("update") || command.equals("remove_key") || command.equals("remove_lower_key") || command.equals("exit"))) {
                    System.out.println("Выполняется команда " + command);
                    String actualCommand = command.trim();
//                    commandManager.executeCommand(actualCommand);
                }
            }
        }
    }

    /**
     * Вспомогательный метод для чтения файла
     *
     * @param filePath путь к файлу
     * @return содержимое файла или null если не удалось прочитать
     */
    private String tryReadFile(String filePath) {
        try {
            ReadableFile readableFile = new ReadFileFiles(filePath);
            return readableFile.getContent();
        } catch (IOException | NullPointerException e) {
            return null;
        }
    }
}
