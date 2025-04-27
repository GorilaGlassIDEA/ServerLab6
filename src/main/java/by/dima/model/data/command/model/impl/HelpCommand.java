package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;

/**
 * Команда, реализующая вывод информации для всех команд
 */
public class HelpCommand extends CommandAbstract {
    private final CommandManager commandManager;
    private StringBuilder builder;

    public HelpCommand(CommandManager commandManager) {
        super("help", "Display help for available commands.");
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        builder = new StringBuilder();
        for (Command command : commandManager.getCommandMap().values()) {
            builder.append(command.getKey()).append(": ").append(command.getHelp());
        }
    }

    @Override
    public String getAnswer() {
        return new String(builder);
    }
}
