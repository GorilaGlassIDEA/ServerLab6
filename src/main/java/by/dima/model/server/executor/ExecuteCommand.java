package by.dima.model.server.executor;

import by.dima.model.data.command.model.CommandManager;
import by.dima.model.common.CommandDTOWrapper;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
    @Setter
public class ExecuteCommand {
    private CommandManager manager;
    private Logger logger;

    public ExecuteCommand(CommandManager manager) {
        this.manager = manager;
    }

    public void execute(CommandDTOWrapper commandDTOWrapper) {
        String cleanStringCommand = commandDTOWrapper.getNameCommand().strip();
        try {
            manager.executeCommand(cleanStringCommand);
            logger.log(Level.CONFIG, "Действие в классе ExecuteCommand выполнено!");
        } catch (RuntimeException e) {
            logger.log(Level.CONFIG, "Ошибка в результате работы класса CommandManager");
        }
    }

}
