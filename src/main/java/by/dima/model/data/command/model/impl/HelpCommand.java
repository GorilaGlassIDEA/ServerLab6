package by.dima.model.data.command.model.impl;

import by.dima.model.data.command.model.CommandManager;
import by.dima.model.data.command.model.model.Command;
import by.dima.model.data.command.model.model.CommandAbstract;

/**
 * Команда, реализующая вывод информации для всех команд
 */
public class HelpCommand extends CommandAbstract {
    private final CommandManager commandManager;

    public HelpCommand(CommandManager commandManager) {
        super("help", "Display help for available commands.");
        this.commandManager = commandManager;
    }

    @Override
    public void execute() {
        for (Command command : commandManager.getCommandMap().values()) {
            System.out.println(command.getKey() + ": " + command.getHelp());
        }
    }

}
