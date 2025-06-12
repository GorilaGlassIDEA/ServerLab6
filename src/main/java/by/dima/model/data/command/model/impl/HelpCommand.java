package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;

import java.util.ResourceBundle;

/**
 * Команда, реализующая вывод информации для всех команд
 */
public class HelpCommand extends CommandAbstract {
    private final CommandManager commandManager;
    private StringBuilder builder;

    public HelpCommand(CommandManager commandManager, ResourceBundle bundle) {
        super("help", bundle.getString("help.command.description"));
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        for (Command command : commandManager.getCommandMap().values()) {
            builder.append(command.getKey()).append(": ").append(command.getHelp()).append("\n");
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder).strip();
    }
}
