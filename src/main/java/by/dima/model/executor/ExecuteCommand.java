package by.dima.model.executor;

import by.dima.model.data.command.model.CommandManager;
import lombok.Setter;

import java.util.logging.Logger;

public class ExecuteCommand {
    private final CommandManager manager;
    @Setter
    private Logger logger;

    public ExecuteCommand(CommandManager manager) {
        this.manager = manager;
    }

    public void execute(String command, String arg, String jsonRoute) {

    }

    public void execute(String command, String arg) {

    }

    public void execute(String command) {
        String cleanStringCommand = command.trim().strip();
        if (manager.getCommandMap().keySet().contains(cleanStringCommand)) {
            manager.executeCommand(cleanStringCommand);
        }
    }


}
