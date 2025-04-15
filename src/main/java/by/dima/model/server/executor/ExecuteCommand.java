package by.dima.model.server.executor;

import by.dima.model.data.command.model.CommandManager;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.logging.Logger;

@NoArgsConstructor
public class ExecuteCommand {
    private CommandManager manager;
    @Setter
    private Logger logger;

    public ExecuteCommand(CommandManager manager) {
        this.manager = manager;
    }

    public void execute(String command) {
        String cleanStringCommand = command.trim().strip();
        if (manager.getCommandMap().containsKey(cleanStringCommand)) {
            manager.executeCommand(cleanStringCommand);
        }
    }

    private String cleanString(String command) {
        return command.trim().strip();
    }

}
